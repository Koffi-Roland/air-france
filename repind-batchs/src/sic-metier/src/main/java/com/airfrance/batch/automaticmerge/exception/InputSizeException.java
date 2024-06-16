package com.airfrance.batch.automaticmerge.exception;

import com.airfrance.batch.automaticmerge.model.OutputRecord;
import lombok.Getter;

import java.util.List;

@Getter
public class InputSizeException extends Exception{

    private final List<String> skippedGins;
    private final boolean shouldSkipLine;

    private OutputRecord outputRecord;

    public InputSizeException(String message, List<String> skippedGins, boolean shouldSkipLine) {
        super(message);
        this.skippedGins = skippedGins;
        this.shouldSkipLine = shouldSkipLine;
    }

    public InputSizeException(String message, List<String> skippedGins, boolean shouldSkipLine, OutputRecord outputRecord) {
        super(message);
        this.skippedGins = skippedGins;
        this.shouldSkipLine = shouldSkipLine;
        this.outputRecord = outputRecord;
    }
}
