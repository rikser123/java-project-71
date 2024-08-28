package hexlet.code;

class ParserParameters {
    private String firstFileContent;
    private String secondFileContent;
    private String fileExtension;

    ParserParameters(String firstFileContent, String secondFileContent, String fileExtension) {
        this.firstFileContent = firstFileContent;
        this.secondFileContent = secondFileContent;
        this.fileExtension = fileExtension;
    }

    public String getFirstFileContent() {
        return firstFileContent;
    }

    public String getSecondFileContent() {
        return secondFileContent;
    }

    public String getFileExtension() {
        return fileExtension;
    }
}
