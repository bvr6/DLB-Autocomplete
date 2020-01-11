import java.util.Arrays;
import java.util.Collections;
public class DLB {
    public Node root;

    // Return a node's character information, returns the '0' character if there is no node (for testing purposes)
    public char returnCharacter(Node n){
        if (n == null)
            return '0';
        else
            return n.character;
    }

    // Simple isEmpty() method
    public boolean isEmpty(){
        if (root == null)
            return true;
        return false;
    }

    /**
     * Adds a String to the DLB. If there is nothing in the DLB, it will automatically create a root and add nodes from there.
     * @param s String that is to be added to the DLB
     * @return true if successfully added, false if not added because it is already in the dictionary
     */

    public boolean add(String s){
        Node currentNode = root;
        char c = s.charAt(0);
        if (this.isEmpty()){
            root = new Node (null, null, s.charAt(0));
        }
        currentNode = root;
        for (int i = 0; i < s.length(); i++){
            c = s.charAt(i);
            if (currentNode.character == '0'){
                currentNode.character = c; 
            }
            if (currentNode.character == c){
                if (currentNode.child == null){
                    currentNode.child = new Node (null, null, '0');
                }
                currentNode = currentNode.child;
            }
            else {
                boolean isFound = false;
                while (currentNode.next != null && !isFound){
                    currentNode = currentNode.next;
                    if (currentNode.character == c){
                        isFound = true;
                        if (currentNode.child == null){
                            currentNode.child = new Node (null, null, '0');
                        }
                        currentNode = currentNode.child;
                    }
                }
                if (!isFound){
                    currentNode.next = new Node (null, null, c);
                    currentNode.next.child = new Node (null, null, '0');
                    currentNode = currentNode.next.child;
                }
            }
        }
        if (currentNode.character == '^'){
            return false; // There's already a word in this spot
        }
        if (currentNode.character == '0'){
            currentNode.character = '^';
            currentNode.put(s, 0);
        }
        return true; // Added successfully
    }

    /**
     * Returns true if the string provided is in the DLB, returns false if otherwise
     * @param s String that is being searched
     * @return value of the string in the DLB, -1 if otherwise
     */
    public int findString(String s){
        Node currentNode = root;
        if (root == null){
            return -1;
        }
        for (int i = 0; i < s.length(); i++){
            char c = s.charAt(i);
            if (currentNode.character == c){
                if (currentNode.child == null){
                    return -1;
                }
                currentNode = currentNode.child;
            }
            else {
                boolean isFound = false;
                while (currentNode.next != null && !isFound){
                    currentNode = currentNode.next;
                    if (currentNode.character == c){
                        isFound = true;
                        if (currentNode.child == null){
                            return -1;
                        }
                        currentNode = currentNode.child;
                    }
                }
                if (!isFound){
                    return -1;
                }
            }
        }
        if (currentNode.character == '^'){
            return currentNode.value;
        }
        return -1;
    }

    public String[] findSuggestions(String s){
        Node currentNode = root;
        if (root == null){
            return null;
        }
        for (int i = 0; i < s.length(); i++){
            char c = s.charAt(i);
            if (currentNode.character == c){
                if (currentNode.child == null){
                    return null;
                }
                currentNode = currentNode.child;
            }
            else {
                boolean isFound = false;
                while (currentNode.next != null && !isFound){
                    currentNode = currentNode.next;
                    if (currentNode.character == c){
                        isFound = true;
                        if (currentNode.child == null){
                            return null;
                        }
                        currentNode = currentNode.child;
                    }
                }
                if (!isFound){
                    return null;
                }
            }
        }
        // at the point of the string...
        String[] results = new String[5];
        results = findSuggestionsRecursive(currentNode, results);
        return results;
    }

    public int findEmptyIndex(String[] array){
        for (int i = 0; i < array.length; i++){
            if (array[i] == null)
                return i;
        }
        return -1;
    }

    public String[] findSuggestionsRecursive(Node currentNode, String[] results){
        if (findEmptyIndex(results) == -1){
            return results;
        }
        if (currentNode.character == '^'){
            results[findEmptyIndex(results)] = currentNode.s;
        }
        if (currentNode.child != null){
            results = findSuggestionsRecursive(currentNode.child, results);
        }
        if (currentNode.next != null){
            results = findSuggestionsRecursive(currentNode.next, results);
        }
        return results;
    }

    public String[] findWeightedSuggestions(String s){
        Node currentNode = root;
        if (root == null){
            return null;
        }
        for (int i = 0; i < s.length(); i++){
            char c = s.charAt(i);
            if (currentNode.character == c){
                if (currentNode.child == null){
                    return null;
                }
                currentNode = currentNode.child;
            }
            else {
                boolean isFound = false;
                while (currentNode.next != null && !isFound){
                    currentNode = currentNode.next;
                    if (currentNode.character == c){
                        isFound = true;
                        if (currentNode.child == null){
                            return null;
                        }
                        currentNode = currentNode.child;
                    }
                }
                if (!isFound){
                    return null;
                }
            }
        }
        // at the point of the string...
        String[] results = new String[5];
        String[] test = findSuggestionsRecursive(currentNode, results);
        int index = findEmptyIndex(results);
        for (int i = 0; i < index; i++){
            for (int j = 0; j < index; j++){
                if (findString(test[i]) < findString(test[j])){
                    String temp = results[i];
                    results[i] = results[j];
                    results[j] = temp;
                }
            }
        }
        System.out.println(Arrays.toString(test));
        return test;
    }

}