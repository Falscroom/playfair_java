package sample;

import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class SimpleController {

    public TextField key_word;
    public TextField text;
    public TextField coded_text;
    public char[][] alphabet_matrix;

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
/*        if(arr[0].simbol == arr[1].simbol) { //ДВА ОДИНАКОВЫХ СИМВОЛА! С добавлением функции replace repeats такая ситуация невозможна!
            return 1;
        }*/
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
                arr[0].j ++;
                arr[1].j ++;
                if(arr[0].j > 4) arr[0].j = 0;
                if(arr[1].j > 4) arr[1].j = 0;
                for (int i = 0;i < 2;i++)
                    coded_text.appendText(Character.toString(alphabet_matrix[arr[i].i][arr[i].j]));
                break;
            case 3:
                arr[0].i ++;
                arr[1].i ++;
                if(arr[0].i > 4) arr[0].i = 0;
                if(arr[1].i > 4) arr[1].i = 0;
                for (int i = 0;i < 2;i++)
                    coded_text.appendText(Character.toString(alphabet_matrix[arr[i].i][arr[i].j]));
                break;
            case 4:
                int buf_j = arr[0].j;
                arr[0].j = arr[1].j;
                arr[1].j = buf_j;
                for (int i = 0;i < 2;i++)
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


    public void code_decode(ActionEvent actionEvent) {
        alphabet_matrix = new char[5][5];
        StringBuffer alphabet = new StringBuffer("ABCDEFGHIJKLMNOPRSTUVWXYZ".subSequence(0,25));
        StringBuffer str_key_word = new StringBuffer(key_word.getText().toUpperCase().subSequence(0, key_word.getText().length()));


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
        str_key_word = new StringBuffer(str_key_word.toString().replaceAll(" ","").subSequence(0,str_key_word.toString().replaceAll(" ","").length()));
        alphabet = new StringBuffer(alphabet.toString().replaceAll(" ","").subSequence(0,alphabet.toString().replaceAll(" ","").length()));

        for(int i = 0;i<5;i++) {
            for(int j = 0;j<5;j++) {
                if(i * 5 + j < str_key_word.length())
                    alphabet_matrix[i][j] = str_key_word.charAt(i * 5 + j);
                else
                    alphabet_matrix[i][j] = alphabet.charAt(i * 5 + j  - str_key_word.length());
            }
        }
        StringBuffer our_text = new StringBuffer(text.getText().toUpperCase().replaceAll(" ", "").subSequence(0, text.getText().replaceAll(" ", "").length()));
        our_text = replace_repeats(our_text);
        cordinate[] cord_arr;
        for(int i = 1;i < our_text.length(); i ++) {
            if((i - 1)%2 == 0) {
               cord_arr = get_cordinate(our_text.charAt(i - 1),our_text.charAt(i));
               code_it(which_case(cord_arr),cord_arr);
           }

        }
    }
}
