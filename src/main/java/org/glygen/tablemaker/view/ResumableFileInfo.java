package org.glygen.tablemaker.view;

import java.io.File;
import java.util.HashSet;

/**
 * by fanxu
 */
public class ResumableFileInfo {

    public int      resumableChunkSize;
    public long     resumableTotalSize;
    public String   resumableIdentifier;
    public String   resumableFilename;
    public String   resumableRelativePath;

    public static class ResumableChunkNumber {
        public ResumableChunkNumber(int number) {
            this.number = number;
        }

        public int number;

        @Override
        public boolean equals(Object obj) {
            return obj instanceof ResumableChunkNumber
                    ? ((ResumableChunkNumber)obj).number == this.number : false;
        }

        @Override
        public int hashCode() {
            return number;
        }
    }

    //Chunks uploaded
    public HashSet<ResumableChunkNumber> uploadedChunks = new HashSet<ResumableChunkNumber>();

    public String resumableFilePath;

    public boolean checkIfUploadFinished() {
        //check if upload finished
        int count = (int) Math.ceil(((double) resumableTotalSize) / ((double) resumableChunkSize));
        for(int i = 1; i <= count; i ++) {
            if (!uploadedChunks.contains(new ResumableChunkNumber(i))) {
                return false;
            }
        }

        //Upload finished, change filename.
        File file = new File(resumableFilePath);
        String new_path = file.getAbsolutePath().substring(0, file.getAbsolutePath().length() - ".temp".length());
        file.renameTo(new File(new_path));
        return true;
    }
	public boolean valid() {
		if (resumableChunkSize < 0 || resumableTotalSize < 0
                || isEmpty(resumableIdentifier)
                || isEmpty(resumableFilename)
                || isEmpty(resumableRelativePath)) {
            return false;
        } else {
            return true;
        }
	}
	
	public static boolean isEmpty(String value) {
        return value == null || "".equals(value);
    }
}