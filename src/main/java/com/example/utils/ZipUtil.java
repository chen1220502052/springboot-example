package com.example.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.fusesource.hawtbuf.BufferOutputStream;

/**
 * https://wiki.sei.cmu.edu/confluence/display/java/IDS04-J.+Safely+extract+files+from+ZipInputStream
 * @author chenqiankun
 *
 */
public class ZipUtil {


    static final int BUFFER = 512;
    static final long TOOBIG = 0x6400000; // Max size of unzipped data, 100MB
    static final int TOOMANY = 1024;      // Max number of files
    
    
    // Noncompliant Code Example 
    /**
     * This noncompliant code fails to validate the name of the file that is being unzipped. 
     * It passes the name directly to the constructor of FileOutputStream. 
     * It also fails to check the resource consumption of the file that is being unzipped. 
     * It permits the operation to run to completion or until local resources are exhausted.
     * @param filename
     * @throws java.io.IOException
     */
    @Deprecated
    public static final void unzipFirst(String filename) throws java.io.IOException{
        FileInputStream fis = new FileInputStream(filename);
        ZipInputStream zis = new ZipInputStream(new BufferedInputStream(fis));
        ZipEntry entry;
        try{
            while((entry = zis.getNextEntry()) != null){
                System.out.println("Extracting: " + entry);
                int count;
                byte data[] = new byte[BUFFER];
                
                // Write the files to the disk
                FileOutputStream fos = new FileOutputStream(entry.getName());
                BufferedOutputStream dest = new BufferedOutputStream(fos, BUFFER);
                while((count = zis.read(data, 0, BUFFER)) != -1){
                    dest.write(data, 0, count);
                }
                dest.flush();
                dest.close();
                zis.closeEntry();
            }
            
        }finally{
            zis.close();
        }
    }
    
    /**
     * This noncompliant code attempts to overcome the problem by calling the method ZipEntry.getSize() 
     * to check the uncompressed file size before uncompressing it. 
     * Unfortunately, a malicious attacker can forge the field in the ZIP file that purports to show the uncompressed size of the file,
     *  so the value returned by getSize() is unreliable, and local resources may still be exhausted.
     * @param filename
     * @throws java.io.IOException
     */
    @Deprecated
    public static final void unzipSecond(String filename) throws java.io.IOException{
        FileInputStream fis = new FileInputStream(filename);
        ZipInputStream zis = new ZipInputStream(new BufferedInputStream(fis));
        ZipEntry entry;
        try{
            while((entry = zis.getNextEntry()) != null){
                System.out.println("Extracting: " + entry);
                int count;
                byte data[] = new byte[BUFFER];
                
                // Write the files to the disk, but only if the file is not insanely big
                if(entry.getSize() > TOOBIG){
                    throw new IllegalStateException("File to be unzipped is huge.");
                }
                if(entry.getSize() == -1){
                    throw new IllegalStateException("File to be unzipped might be huge.");
                }
                
                FileOutputStream fos = new FileOutputStream(entry.getName());
                BufferedOutputStream dest = new BufferedOutputStream(fos, BUFFER);
                while((count = zis.read(data, 0, BUFFER)) != -1){
                    dest.write(data, 0, count);
                }
                dest.flush();
                dest.close();
                zis.closeEntry();
            }
        }finally{
            
        }
    }
    
    private static final String validateFileName(String fileName, String intendedDir) throws java.io.IOException{
        File f = new File(fileName);
        String canonicalPath = f.getCanonicalPath();
        File iD = new File(intendedDir);
        String canonicalID = iD.getCanonicalPath();
        
        if(canonicalPath.startsWith(canonicalID)){
            return canonicalPath;
        }else{
            throw new IllegalStateException("File is outside extraction target directory.");
        }
    }
    
    public static final void unzip(String filename) throws java.io.IOException{
        FileInputStream fis = new FileInputStream(filename);
        ZipInputStream zis = new ZipInputStream(new BufferedInputStream(fis));
        ZipEntry entry;
        int entries = 0;
        int total = 0;
        try{
            while((entry = zis.getNextEntry())!= null){
                System.out.println("Extracting: " + entry);
                int count;
                byte data[] = new byte[BUFFER];
                
                // Write the files to the disk, but ensure that the filename is valid,
                // and that the file is not insanely big
                String name = validateFileName(entry.getName(), ".");
                if(entry.isDirectory()){
                    System.out.println("Creating directory " + name);
                    new File(name).mkdir();
                    continue;
                }
                FileOutputStream fos = new FileOutputStream(name);
                BufferedOutputStream dest = new BufferedOutputStream(fos, BUFFER);
                while(total + BUFFER <= TOOBIG && (count = zis.read(data, 0, BUFFER)) != -1){
                    dest.write(data, 0, count);
                    total += count;
                }
                dest.flush();
                dest.close();
                zis.closeEntry();
                entries++;
                if(entries > TOOMANY){
                    throw new IllegalStateException("Too many files to unzip.");
                }
                if(total + BUFFER > TOOBIG){
                    throw new IllegalStateException("File being unzipped is too big.");
                }
            }
        }finally{
            zis.close();
        }
    }
    
    
}
