package renderer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Locale;

import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL20.glGetShaderInfoLog;

public class Shader {
    private int shaderProgramID;
    private String vertexSource;
    private String fragmentSource;
    private String filepath; // for error handling

    public Shader(String _filepath) {
        this.filepath = _filepath;
        //verbose java shit
        try {
            String source = new String(Files.readAllBytes(Paths.get(filepath)));
            String[] splitString = source.split("(#type)( )+([a-zA-Z]+)");

            //fucking syntactic jujitsu
            int index = source.indexOf("#type") + 6; //beginning of shader type
            int eol = source.indexOf("\r\n", index);
            String firstPattern = source.substring(index, eol).trim();

            index = source.indexOf("#type", eol) + 6;
            eol = source.indexOf("\r\n", index);
            String secondPattern = source.substring(index, eol).trim();

            if(firstPattern.toLowerCase(Locale.ROOT).equals("vertex")) {
                vertexSource = splitString[1];
            } else if(firstPattern.equals("fragment")) {
                fragmentSource = splitString[1];
            } else{
                throw new IOException("Unexpected token '" + firstPattern + "' in '" + filepath + "'");
            }
            if(secondPattern.equals("vertex")) {
                vertexSource = splitString[2];
            } else if(secondPattern.equals("fragment")) {
                fragmentSource = splitString[2];
            } else{
                throw new IOException("Unexpected token '" + firstPattern + "' in '" + filepath + "'");
            }

        } catch(IOException e) {
            e.printStackTrace();
            assert false: "Error: could not open/process shader file: '" + filepath + "'.";
        }
    }

    public void compile() {
        int vertexID, fragmentID;
        //===========================================
        //  Compile vertex shader
        //===========================================
        vertexID=glCreateShader(GL_VERTEX_SHADER);
        glShaderSource(vertexID,vertexSource);
        glCompileShader(vertexID);
        int success=glGetShaderi(vertexID,GL_COMPILE_STATUS);
        if(success==GL_FALSE) {
            int len=glGetShaderi(vertexID,GL_INFO_LOG_LENGTH);
            System.out.println("Error: '"+ filepath + "'\n\tVertex shader compilation failed");
            System.out.println(glGetShaderInfoLog(vertexID,len));
            assert false:"";
        }

        //===========================================
        //  Compile fragment shader
        //===========================================
        fragmentID=glCreateShader(GL_FRAGMENT_SHADER);
        glShaderSource(fragmentID,fragmentSource);
        glCompileShader(fragmentID);
        success=glGetShaderi(fragmentID,GL_COMPILE_STATUS);
        if(success==GL_FALSE) {
            int len=glGetShaderi(fragmentID,GL_INFO_LOG_LENGTH);
            System.out.println("Error: '"+ filepath + "'\n\tFragment shader compilation failed");
            System.out.println(glGetShaderInfoLog(fragmentID,len));
            assert false:"";
        }

        //===========================================
        //  Link & attach shaders
        //===========================================
        shaderProgramID=glCreateProgram();
        glAttachShader(shaderProgramID,vertexID);
        glAttachShader(shaderProgramID,fragmentID);
        glLinkProgram(shaderProgramID);
        success=glGetProgrami(shaderProgramID,GL_LINK_STATUS);
        if(success==GL_FALSE) {
            int len=glGetShaderi(shaderProgramID,GL_INFO_LOG_LENGTH);
            System.out.println("Error: '"+ filepath + "'\n\tShader linking failed");
            System.out.println(glGetShaderInfoLog(shaderProgramID,len));
            assert false:"";
        }
    }

    public void use() {
        glUseProgram(shaderProgramID);
    }

    public void detach() {
        glUseProgram(0);
    }

}
