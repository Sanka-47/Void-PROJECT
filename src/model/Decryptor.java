
package model;

public class Decryptor {
    
    public String decrypt(String text, int key) {
        
        char[] chars = text.toCharArray();
        
        char[] decryptedChars = shiftCharacters(chars, key);
        return new String(decryptedChars);        
    }
    
    private char[] shiftCharacters(char[] chars, int key) {
        
        char[] shifted = new char[chars.length];
        for (int i = 0; i < chars.length; i++) {
            shifted[i] = (char) (chars[i] - key);
        }
        return shifted;
    }
    
}
