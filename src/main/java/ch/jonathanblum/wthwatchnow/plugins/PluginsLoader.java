package ch.jonathanblum.wthwatchnow.plugins;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.jar.JarFile;
import java.lang.RuntimeException;

/**
 * Classe gérant le chargement et la validation des plugins
 * @author Lainé Vincent (dev01, http://vincentlaine.developpez.com/ )
 *
 */
public class PluginsLoader {

	private String[] files;
	
        private ArrayList classCollectionPlugins;
	
	/**
	 * Constructeur par défaut
	 *
	 */
	public PluginsLoader(){
                this.classCollectionPlugins = new ArrayList();
	}
	
	/**
	 * Constucteur initialisant le tableau de fichier à charger.
	 * @param files Tableau de String contenant la liste des fichiers à charger.
	 */
	public PluginsLoader(String[] files){
		this();
		this.files = files;
	}
	
	/**
	 * Défini l'ensemble des fichiers à charger
	 * @param files
	 */
	public void setFiles(String[] files ){
		this.files = files;
	}
        
        /**
         * Fonction de chargement de tous les plugins de type CollectionPlugins
         * @return Une collection de CollectionPlugins contenant les instances des plugins
         * @throws Exception si file = null ou file.length = 0;
         */
        public ICollectionItemPlugin[] loadAllCollectionPlugins() throws Exception {
            this.initializeLoader();
            
            ICollectionItemPlugin[] tmpPlugins = new ICollectionItemPlugin[this.classCollectionPlugins.size()];
            
            for (int i = 0; i < tmpPlugins.length; i++) {
                 tmpPlugins[i] = (ICollectionItemPlugin)((Class)this.classCollectionPlugins.get(i)).newInstance();
            }
            return tmpPlugins;
        }
        

	private void initializeLoader() throws Exception{
		//On vérifie que la liste des plugins à charger à été initialisé
		if(this.files == null || this.files.length == 0 ){
			throw new Exception("No files specified");
		}

                // Prevent plugins from being double-loaded.
                if(!this.classCollectionPlugins.isEmpty()) {
                    return;
                }
		
		File[] f = new File[this.files.length];
                //Pour charger le .jar en memoire
		URLClassLoader loader;
		//Pour la comparaison de chaines
		String tmp = "";
		//Pour le contenu de l'archive jar
		Enumeration enumeration;
		//Pour déterminer quels sont les interfaces implémentées
		Class tmpClass = null;
		
		for(int index = 0 ; index < f.length ; index ++ ){
			
			f[index] = new File(this.files[index]);
			
			if( !f[index].exists() ) {
				break;
			}
			
			URL u = f[index].toURL();
			//On créer un nouveau URLClassLoader pour charger le jar qui se trouve ne dehors du CLASSPATH
			loader = new URLClassLoader(new URL[] {u}); 
			
			//On charge le jar en mémoire
			JarFile jar = new JarFile(f[index].getAbsolutePath());
			
			//On récupére le contenu du jar
			enumeration = jar.entries();
			
			while(enumeration.hasMoreElements()){
				
				tmp = enumeration.nextElement().toString();

				//On vérifie que le fichier courant est un .class (et pas un fichier d'informations du jar )
				if(tmp.length() > 6 && tmp.substring(tmp.length()-6).compareTo(".class") == 0) {
					
					tmp = tmp.substring(0,tmp.length()-6);
					tmp = tmp.replaceAll("/",".");
					System.out.println(tmp);                               
					tmpClass = Class.forName(tmp ,true,loader);
					
                                    for (Class intf : tmpClass.getInterfaces()) {
                                        //Une classe ne doit pas appartenir à deux catégories de plugins différents.
                                        //Si tel est le cas on ne la place que dans la catégorie de la première interface correct
                                        // trouvée
                                        switch(intf.getName()) {
                                            case "ch.jonathanblum.wthwatchnow.plugins.ICollectionItemPlugin":
                                                this.classCollectionPlugins.add(tmpClass);
                                                break;
                                            default:
                                                throw new RuntimeException("PluginsLoader.initializeLoader: " + intf.getName());
                                                
                                        }
                                    }
					
				}
			}
		}	
	}	
}