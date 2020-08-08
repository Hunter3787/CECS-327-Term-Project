package cecs327termproject;

/**
*  FileBlock class, splits files into blocks for data transfer.
*  -Class may not be needed.-
*  CECS 327 Term Project.
*  @author Nickolaus Marshall-Eminger
*  Date: July 31, 2020
*/
class FileBlock {
    
    private byte[] bytes;
    private static final int BLOCK_SIZE = 1024;
    
    //TODO: Constructor and data parsing into bytes array if needed. 
    
    public static int getBlockSize(){
        return BLOCK_SIZE;
    }
}
