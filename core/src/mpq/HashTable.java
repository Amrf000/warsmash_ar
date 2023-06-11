package mpq;

import mpq.data.HashTableEntry;

import java.util.Map;

public class HashTable {
    public static final int BLOCK_EMPTY_ALWAYS = 0xFFFFFFFF;
    private final Map<Long,Entry> bucketArray;

    // raw constructor, assumes every entry is not null and the array is a power of 2
    public HashTable(Map<Long,Entry> entries) {
        bucketArray = entries;
    }

    public int lookupBlock(HashLookup what) throws MPQException {
        if(bucketArray.containsKey(what.hash)){
            return bucketArray.get(what.hash).blockIndex;
        }
//        int mask = bucketArray.length - 1;
//        int index = what.index & mask;
//        for (int pos = index; ; ) {
//            Entry temp = bucketArray[pos];
//            if (temp.blockIndex == BLOCK_EMPTY_ALWAYS) break;
//            if (temp.getHash() == what.hash) return temp.blockIndex;
//            pos = (pos + 1) & mask;
//            if (pos == index) break;
//        }
        throw new MPQException("lookup not found");
    }

    // entry is an internal data type and as such performs no safety checks
    public static class Entry {
        public long hash;
        public short locale;
        public short platform;
        public int blockIndex;

        // raw constructor
        public Entry() {
        }

        public Entry(HashTableEntry source) {
            hash = source.getHash();
            locale = source.getLocale();
            platform = source.getPlatform();
            blockIndex = source.getBlockIndex();
        }

        public long getHash() {
            return hash;
        }

        public short getLocale() {
            return locale;
        }
    }
}
