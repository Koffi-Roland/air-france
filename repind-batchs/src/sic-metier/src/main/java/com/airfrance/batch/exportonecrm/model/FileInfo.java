package com.airfrance.batch.exportonecrm.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.nio.file.Path;

@Getter
@Setter
@AllArgsConstructor
public class FileInfo {
    private final Path path;
    private final String fileName;
}
