package ch.jonathanblum.wthwatchnow.broker.model;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author Jonathan Blum <jonathan.blum@eldhar.com>
 */
public class AniListAccessToken {
    private final String access_token;
    private final String token_type;
    private final long expires;
    private final int expires_in;

    public AniListAccessToken(String access_token, String token_type, long expires, int expires_in) {
        this.access_token = access_token;
        this.token_type = token_type;
        this.expires = expires;
        this.expires_in = expires_in;
    }

    public String getAccess_token() {return access_token;}
    public String getAccess_tokenParam() { return "access_token="+access_token;}
    public String getToken_type() {return token_type;}
    public long getExpires() {return expires;}
    public int getExpires_in() {return expires_in;}
    public boolean isExpired() { return ((System.currentTimeMillis()/1000L) >= expires); }

    @Override
    public String toString() {
        return "{access_token=" + access_token + ", token_type=" + token_type + ", expires=" + expires + ", expires_in=" + expires_in + '}';
    } 
}
