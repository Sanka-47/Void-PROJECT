
package model;

public class Encryptor {
    public String encrypt(String text, int key) {
        char[] chars = text.toCharArray();
        
        char[] encryptedChars = shiftCharacters(chars, key);
        
        return new String(encryptedChars);
    }
    
    private char[] shiftCharacters(char[] chars, int key) {
        
        char[] shifted = new char[chars.length];
        
        for (int i = 0; i < chars.length; i++) {
            shifted[i] = (char) (chars[i] + key);
        }
        return shifted;
    }
    
}
