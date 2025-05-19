
package model;

public final class SecurePasswordFacade {
    
    private Encryptor encryptor;
    
    public SecurePasswordFacade(){
        
        this.encryptor = new Encryptor();
    }
    
    
    public String encryptToFile(String text, int key) {
        String encrypted = encryptor.encrypt(text, key);
        return encrypted;
    }
}
