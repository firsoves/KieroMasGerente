package com.magic.kieromasgerente.Servidor;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.magic.kieromasgerente.MainActivity;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.channels.FileChannel;

@RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
public class SubidaFoto extends AsyncTask<Void, Void, String> {

    public static final String FORM_FILE_UBICACION = "nuevoFotoPlato";
    private static final String LOG = "FilesUploadingTask: ";
    String PHP_FILE_UPLOADING = MainActivity.baseUrlFoto;

    private String lineEnd = "\r\n";
    private String twoHyphens = "--";
    private String boundary = "----WebKitFormBoundary9xFB2hiUhzqbBQ4M";
    private int bytesRead, bytesAvailable, bufferSize;
    private byte[] buffer;
    private int maxBufferSize = 1 * 1024 * 1024;
    private String filePath;

    public SubidaFoto(String filePath) {
        this.filePath = filePath;
    }

    public static String readStream(InputStream inputStream) throws IOException {
        StringBuffer buffer = new StringBuffer();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        String line;
        while ((line = reader.readLine()) != null) {
            buffer.append(line);
        }

        return buffer.toString();
    }

    @SuppressLint({"LongLogTag", "WrongThread"})
    @Override
    protected String doInBackground(Void... params) {
        // Результат выполнения запроса, полученный от сервера
        String result = null;
        File directory = new File(MainActivity.nombreFotoTemporalPlato + "/");

        result = subidaFotos(MainActivity.nombreFotoTemporalPlatoCompleto); // Загружаю файл на сервер
        Log.d("!!!!!!!!!!!!!!!!!!!!!!!", "Ответ на загрузку фото: " + result);
        return result;
    }

    protected String subidaFotos(String fotoNombre) {
        String respuesta = "";


        FileChannel inChannel = null;
        try {
            inChannel = new FileInputStream(fotoNombre).getChannel();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        FileChannel outChannel = null;
        try {
            fotoNombre = fotoNombre.substring(0, fotoNombre.length() - 12) + MainActivity.idNuevoProducto + ".png";
            outChannel = new FileOutputStream(fotoNombre).getChannel();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try {
            inChannel.transferTo(0, inChannel.size(), outChannel);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (inChannel != null) {
            try {
                inChannel.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (outChannel != null) {
            try {
                outChannel.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        try {
            URL uploadUrl = new URL(PHP_FILE_UPLOADING);
            if (fotoNombre != null) {
                uploadUrl = new URL(PHP_FILE_UPLOADING);
            }

            HttpURLConnection connection = (HttpURLConnection) uploadUrl.openConnection();
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setUseCaches(false);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Connection", "Keep-Alive");
            connection.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
            connection.connect();

            DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());
            outputStream.writeBytes(twoHyphens + boundary + lineEnd);
            if (fotoNombre != null) {
                outputStream.writeBytes("Content-Disposition: form-data; name=\"" + FORM_FILE_UBICACION + "\"; filename=\"" + fotoNombre + "\"" + lineEnd);
            }

            outputStream.writeBytes("Content-Type: image/jpeg" + lineEnd);
            outputStream.writeBytes(lineEnd);
            FileInputStream fileInputStream = new FileInputStream(new File(fotoNombre));
            bytesAvailable = fileInputStream.available();
            bufferSize = Math.min(bytesAvailable, maxBufferSize);
            buffer = new byte[bufferSize];

            bytesRead = fileInputStream.read(buffer, 0, bufferSize);

            while (bytesRead > 0) {
                outputStream.write(buffer, 0, bufferSize);
                bytesAvailable = fileInputStream.available();
                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                bytesRead = fileInputStream.read(buffer, 0, bufferSize);
            }

            outputStream.writeBytes(lineEnd);
            outputStream.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

            int serverResponseCode = connection.getResponseCode();

            fileInputStream.close();
            outputStream.flush();
            outputStream.close();

            if (serverResponseCode == 200) {
                respuesta = readStream(connection.getInputStream());

                File myFile = new File(fotoNombre);

                if (respuesta.equals("success")) { // Если с сервера приходит положительный ответ о записи файла, то удаляю файл с телефона
                    myFile.delete();
                    Log.d(LOG, "Удаляем фото!");
                }

            } else {
                respuesta = readStream(connection.getErrorStream());
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return respuesta;
    }
}
