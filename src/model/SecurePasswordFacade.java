
package model;

public final class SecurePasswordFacade {
    
    private Encryptor encryptor;
    private Decryptor decryptor;
    
    
    public SecurePasswordFacade(){
        this.encryptor = new Encryptor();
        this.decryptor = new Decryptor();
        
    }
    
    public String encryptToFile(String text, int key) {
        String encrypted = encryptor.encrypt(text, key);
        return encrypted;
    }
    
    public String decryptFromFile(String text, int key) {
        return decryptor.decrypt(text, key);
    }
}
