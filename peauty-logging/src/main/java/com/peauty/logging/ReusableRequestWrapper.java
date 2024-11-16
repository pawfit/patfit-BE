package com.peauty.logging;

import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class ReusableRequestWrapper extends HttpServletRequestWrapper {
    private final Charset encoding;
    private final byte[] rawData;

    public ReusableRequestWrapper(HttpServletRequest request) throws IOException {
        super(request);

        String characterEncoding = request.getCharacterEncoding();
        if (characterEncoding == null) {
            characterEncoding = StandardCharsets.UTF_8.name();
        }
        this.encoding = Charset.forName(characterEncoding);

        try (InputStream inputStream = request.getInputStream()) {
            this.rawData = inputStream.readAllBytes();
        }
    }

    public String readBody() {
        return new String(this.rawData, this.encoding);
    }

    @Override
    public ServletInputStream getInputStream() {
        final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(this.rawData);

        return new ServletInputStream() {
            @Override
            public int readLine(byte[] b, int off, int len) throws IOException {
                return super.readLine(b, off, len);
            }

            @Override
            public boolean isFinished() {
                return false;
            }

            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setReadListener(ReadListener listener) {
                // Do nothing
            }

            @Override
            public int read() {
                return byteArrayInputStream.read();
            }
        };
    }

    @Override
    public BufferedReader getReader() {
        return new BufferedReader(new InputStreamReader(getInputStream()));
    }
}