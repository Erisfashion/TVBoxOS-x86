package org.apache.commons.io.input;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.nio.charset.Charset;

public class ReaderInputStream extends InputStream {
    private final Reader reader;
    private final Charset charset;

    public ReaderInputStream(Reader reader) {
        this(reader, Charset.defaultCharset());
    }

    public ReaderInputStream(Reader reader, Charset charset) {
        this.reader = reader;
        this.charset = charset;
    }

    public ReaderInputStream(Reader reader, String charsetName) {
        this(reader, Charset.forName(charsetName));
    }

    @Override
    public int read() throws IOException {
        return reader.read();
    }

    @Override
    public int read(byte[] b, int off, int len) throws IOException {
        char[] cbuf = new char[len];
        int read = reader.read(cbuf, 0, len);
        if (read == -1) return -1;
        byte[] bytes = new String(cbuf, 0, read).getBytes(charset);
        System.arraycopy(bytes, 0, b, off, Math.min(bytes.length, len));
        return read;
    }

    @Override
    public void close() throws IOException {
        reader.close();
    }
}
