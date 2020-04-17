package petros.structs.multibucket;

/*
complexity scale of putInStorer(),findIndexOf()  is low. The worst case is to have more than one Containers in
the linked list but we can't really have too many because each ID is unique(the hashedID is not but it is not very common
to have many same hashedID's too). Best case complexity(which happens most regularly) : O(1)
*/
public class IndexStorer
{
    private Container[] hashArray;

    public IndexStorer(){
        hashArray = new Container[1000];
    }

    // creates a Container that has the index of the elements we put in PQ and their ID's and puts it
    // in hashArray. The index of the Container in hashArray is determined by a hash that is calculated from the ID
    // If the same hash is created 2 or more times then a linked list with all the Containers that have the same index
    // in hashArray is created by placing the next Container in the "next" field of the last Container
    public void putInStorer( int index, int ID)
    {
        int hashedID = hash(ID);
        if(hashArray[hashedID] == null)
            hashArray[hashedID] = new Container(index, ID);
        else{
            Container indexContainer = hashArray[hashedID];
            while (indexContainer.next != null) {
                indexContainer = indexContainer.next;
            }
            indexContainer.next = new Container(index, ID);
        }
    }

    public int findIndexOf(int ID)
    {
        Container indexContainer = findContainerWithID(ID, hash(ID));
        if(indexContainer == null) return -1;
        return indexContainer.index;
    }

    public void changeIndexOf(int ID, int newIndex)
    {
        Container wantedContainer = findContainerWithID(ID, hash(ID));
        wantedContainer.index = newIndex;
    }

    public void deleteElement(int wantedID)
    {
        int hashedID = hash(wantedID);
        Container c = hashArray[hashedID];
        if(c.next == null) hashArray[hashedID] = null; //this is the Container we have to remove
        else if(c.ID == wantedID) hashArray[hashedID] = c.next; //remove the first container of the linked list
        else{
            while(c.next.ID != wantedID){
                c = c.next;
            }
            Container uselessContainer = c.next;
            c.next = uselessContainer.next;
        }
    }


    //private methods


    // returns a hash that is used as index in hashArray.
    // if the ID is bigger than the capacity of the array then the hash becomes the last 3,2 or 1
    // numbers of the ID(from the end and backwards), depending on how big the ID is
    private int hash(int ID)
    {
        if(ID < 1000) return ID;
        int curLength = String.valueOf(ID).length();
        String hashedID = "";
        StringBuilder sID = new StringBuilder(String.valueOf(ID));
        int repetitions = 0;
        while (curLength - 3 != 0 && repetitions != 3){
            hashedID += sID.charAt(curLength-1);
            curLength--; repetitions++;
        }
        return Integer.parseInt(hashedID);
    }

    private Container findContainerWithID(int wantedID, int hashedID)
    {
        Container indexContainer = hashArray[hashedID];
        if(indexContainer == null) return null;

        while (indexContainer.ID != wantedID){
            indexContainer = indexContainer.next;
            if(indexContainer == null) return null;
        }
        return indexContainer;
    }

    private class Container
    {
        int index; int ID; Container next;

        Container(int index, int ID, Container next){
            this(index,ID);
            this.next = next;
        }

        Container(int index, int ID){
            this.index = index;
            this.ID = ID;
        }
    }

}
