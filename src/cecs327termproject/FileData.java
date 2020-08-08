package cecs327termproject;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;

/**
*  FileData class, carries data regarding individual files.
*  CECS 327 Term Project.
*  @author Nickolaus Marshall-Eminger
*  Date: July 31, 2020
*/
public class FileData {
    
    /** Instance of this file. */
    private File file; 
    /** File path as path object. */
    private Path path;
    /** File name as string. */
    private String name;
    /** File size as long. */
    private long size;
    /** Last modified date. */
    private Date date;
    /** ArrayList of file block packets. */
    private ArrayList<FileBlock> blocks;
    
    /**
     * Constructor.
     * @param path Path to file directory.
     * @param name File name in designated path.
     * @throws IOException If file does not exist or is 
     * not present throw exception.
     */
    public FileData(File file, String name) throws IOException{
        this.path = Paths.get(file.getAbsolutePath());       
        this.file = file;
        this.name = name;
        this.date = new Date(file.lastModified());
        this.size = Files.size(path);
    }
    
    /**
     * Returns an instance of the designated File.
     * @return Type File.
     */
    public File getFile(){ 
        return file; 
    }
    
    /**
     * Getter for file path.
     * @return Path object containing the path to the file.
     */
    public Path getPath(){ 
        return path; 
    }
    
    /**
     * Getter for file name.
     * @return Returns String of file name.
     */
    public String getName(){ 
        return name; 
    }
    
    /**
     * Getter for file size.
     * @return Type long of the files size.
     */
    public long getSize(){ 
        return size; 
    }
    
    /**
     * Getter for Date of last modification. 
     * @return Type Date with the current modified date.
     */
    public Date getDate(){ 
        return date; 
    }
    
    /**
     * Getter for the blocks of data for transfer.
     * @return ArrayList of blocks of type FileBlock.
     */
    public ArrayList<FileBlock> getBlocks(){ 
        return blocks; 
    }
    
    /**
     * Setter for the file.
     * @param file Type File, represents an instance of 
     * the actual file referenced.
     */
    public void setFile(File file) { 
        this.file = file; 
    }
    
    /**
     * Setter for the file path. 
     * @param path Type Path object, representing the path to the file.
     */
    public void setPath(Path path) { 
        this.path = path; 
    }
    
    /**
     * Setter for the name of the file.
     * @param name Type String, representing the file name.
     */
    public void setName(String name) { 
        this.name = name; 
    }
    
    /**
     * Setter for the files Size.
     * @param size Type long, representing the size of the file. 
     */
    public void setSize(long size) { 
        this.size = size; 
    }
    
    /**
     * Setter for Date on File.
     * @param date Type Date object that represents the 
     * last modified date time of the file.
     */
    public void setDate(Date date) {
        this.date = date; 
    }
    
    /**
     * Setter for the ArrayList of blocks that will be sued for data transfer.
     * @param blocks ArrayList of FileBlocks that make up the file.
     */
    public void setBlocks(ArrayList<FileBlock> blocks) { 
        this.blocks = blocks; 
    }
    
    /**
     * Overridden toString method.
     * @return String type with file name.
     */
    @Override
    public String toString(){
        return "File name: " + name;
    }
}
