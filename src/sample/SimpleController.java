package sample;

import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class SimpleController {

    public TextField key_word;
    public TextField text;
    public TextField coded_text;
    public char[][] alphabet_matrix;
    public boolean encode = false;

     public class cordinate {
        public int i,j;
        public char simbol;
        cordinate(int i,int j,char simbol) {
            this.i = i;
            this.j = j;
            this.simbol = simbol;
        }
    }
     public cordinate[] get_cordinate(char a,char b) {
        cordinate[] cord_array = new cordinate[2];
        for(int i = 0;i < 5;i++){
            for(int j = 0;j < 5 ;j++) {
                if(alphabet_matrix[i][j] == a) {
                    cord_array[0] = new cordinate(i,j,a);
                }
                if(alphabet_matrix[i][j] == b) {
                    cord_array[1] = new cordinate(i,j,b);
                }

            }
        }
        return cord_array;
    }
    public int which_case(cordinate[] arr) {
        if(arr[0].i == arr[1].i) { // В ОДНОЙ СТРОКЕ!
            return 2;
        }
        if(arr[0].j == arr[1].j) { // В ОДНОМ СТОЛБЦЕ!
            return 3;
        }
        return 4; // ОБРАЗУЮТ ПРЯМОУГОЛЬНИК!
    }
    public void code_it(int case_num,cordinate[] arr) {
        switch (case_num) {
            case 2:
                if(encode) {
                    arr[0].j --;
                    arr[1].j --;
                }
                else {
                    arr[0].j ++;
                    arr[1].j ++;
                }
                if(arr[0].j > 4) arr[0].j = 0;
                if(arr[1].j > 4) arr[1].j = 0;

                if(arr[0].j < 0) arr[0].j = 4;
                if(arr[1].j < 0) arr[1].j = 4;

                for (int i = 0;i < 2;i++)
                    if(encode)
                        text.appendText(Character.toString(alphabet_matrix[arr[i].i][arr[i].j]));
                    else
                        coded_text.appendText(Character.toString(alphabet_matrix[arr[i].i][arr[i].j]));
                break;
            case 3:
                if(encode) {
                    arr[0].i --;
                    arr[1].i --;
                }
                else {
                    arr[0].i ++;
                    arr[1].i ++;
                }
                if(arr[0].i > 4) arr[0].i = 0;
                if(arr[1].i > 4) arr[1].i = 0;

                if(arr[0].i < 0) arr[0].i = 4;
                if(arr[1].i < 0) arr[1].i = 4;

                for (int i = 0;i < 2;i++)
                    if(encode)
                        text.appendText(Character.toString(alphabet_matrix[arr[i].i][arr[i].j]));
                    else
                        coded_text.appendText(Character.toString(alphabet_matrix[arr[i].i][arr[i].j]));
                break;
            case 4:
                int buf_j = arr[0].j;
                arr[0].j = arr[1].j;
                arr[1].j = buf_j;
                for (int i = 0;i < 2;i++)
                    if(encode)
                        text.appendText(Character.toString(alphabet_matrix[arr[i].i][arr[i].j]));
                    else
                        coded_text.appendText(Character.toString(alphabet_matrix[arr[i].i][arr[i].j]));
                break;
        }
    }
    public StringBuffer replace_repeats(StringBuffer str) {
        StringBuffer str_b = new StringBuffer(str.subSequence(0,str.length()));
        for(int i = 1;i< str_b.length();i++) {
            if(str_b.charAt(i - 1) == str_b.charAt(i)) {
                str_b.insert(i,'X');
            }
        }
        if(str_b.length() % 2 == 1 ) str_b.insert(str_b.length(),'X');
        return str_b;
    }
    public void delete_repeats(StringBuffer str_key_word,StringBuffer alphabet) { // Вообще можно сделать, чтобы сразу удалялись пробелы, но мне лень думать.
        for(int i = 0;i<str_key_word.length();i++) {
            char simbol = str_key_word.charAt(i);
            for(int j = i + 1;j < str_key_word.length();j++) {
                if(simbol == str_key_word.charAt(j)) {
                    str_key_word.setCharAt(j,' ');
                }
            }
            for(int j = 0;j < alphabet.length();j++) {
                if(simbol == alphabet.charAt(j)) {
                    alphabet.setCharAt(j, ' ');
                }
            }
        }
    }
    public void fill_the_matrix(StringBuffer str_key_word,StringBuffer alphabet){
        for(int i = 0;i<5;i++) {
            for(int j = 0;j<5;j++) {
                if(i * 5 + j < str_key_word.length())
                    alphabet_matrix[i][j] = str_key_word.charAt(i * 5 + j);
                else
                    alphabet_matrix[i][j] = alphabet.charAt(i * 5 + j  - str_key_word.length());
            }
        }
    }
    public void fill_last_input(StringBuffer our_text) {
        cordinate[] cord_arr;
        for(int i = 1;i < our_text.length(); i ++) {
            if((i - 1)%2 == 0) {
                cord_arr = get_cordinate(our_text.charAt(i - 1),our_text.charAt(i));
                code_it(which_case(cord_arr),cord_arr);
            }
        }
    }
    public void encode_it(StringBuffer str) {
        encode = true;
        fill_last_input(str);
        encode = false;
    }
    public void ClickButton(ActionEvent actionEvent) {
        //////////////
        alphabet_matrix = new char[5][5];
        StringBuffer alphabet = new StringBuffer("ABCDEFGHIJKLMNOPRSTUVWXYZ".subSequence(0,25));
        StringBuffer str_key_word = new StringBuffer(key_word.getText().toUpperCase().subSequence(0, key_word.getText().length()));
        StringBuffer our_text = replace_repeats(new StringBuffer(text.getText().toUpperCase().replaceAll(" ", "").subSequence(0, text.getText().replaceAll(" ", "").length())));
        /////////////
        if(our_text.length() > 0) {
            delete_repeats(str_key_word,alphabet);
            str_key_word = new StringBuffer(str_key_word.toString().replaceAll(" ","").subSequence(0,str_key_word.toString().replaceAll(" ","").length()));
            alphabet = new StringBuffer(alphabet.toString().replaceAll(" ","").subSequence(0,alphabet.toString().replaceAll(" ","").length()));
            fill_the_matrix(str_key_word,alphabet);
            fill_last_input(our_text);
        }
        else {
            StringBuffer coded_str = new StringBuffer(coded_text.getText().toUpperCase().subSequence(0, coded_text.getText().length()));

            delete_repeats(str_key_word,alphabet);
            str_key_word = new StringBuffer(str_key_word.toString().replaceAll(" ","").subSequence(0,str_key_word.toString().replaceAll(" ","").length()));
            alphabet = new StringBuffer(alphabet.toString().replaceAll(" ","").subSequence(0,alphabet.toString().replaceAll(" ","").length()));
            fill_the_matrix(str_key_word,alphabet);
            encode_it(coded_str);

        }

    }
}
