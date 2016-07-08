/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

/**
 *
 * @author wang_xiaox
 */
public class Encription {

    public static String Encryption(String cph) {
        String str = "06";
        switch (cph.substring(5, 6)) {
            case "A":
                str = str + "0F";
                break;

            case "B":
                str = str + "E6";
                break;

            case "C":
                str = str + "60";
                break;

            case "D":
                str = str + "1E";
                break;

            case "E":
                str = str + "4F";
                break;

            case "F":
                str = str + "25";
                break;

            case "G":
                str = str + "C7";
                break;

            case "H":
                str = str + "52";
                break;

            case "J":
                str = str + "AA";
                break;

            case "K":
                str = str + "E5";
                break;

            case "L":
                str = str + "06";
                break;

            case "M":
                str = str + "75";
                break;

            case "N":
                str = str + "11";
                break;

            case "P":
                str = str + "3F";
                break;

            case "Q":
                str = str + "DD";
                break;

            case "R":
                str = str + "B1";
                break;

            case "S":
                str = str + "4E";
                break;

            case "T":
                str = str + "A4";
                break;

            case "U":
                str = str + "61";
                break;

            case "V":
                str = str + "99";
                break;

            case "W":
                str = str + "19";
                break;

            case "X":
                str = str + "37";
                break;

            case "Y":
                str = str + "29";
                break;

            case "Z":
                str = str + "54";
                break;

            case "0":
                str = str + "38";
                break;

            case "1":
                str = str + "A1";
                break;

            case "2":
                str = str + "69";
                break;

            case "3":
                str = str + "3D";
                break;

            case "4":
                str = str + "43";
                break;

            case "5":
                str = str + "D2";
                break;

            case "6":
                str = str + "F2";
                break;

            case "7":
                str = str + "F5";
                break;

            case "8":
                str = str + "B4";
                break;

            case "9":
                str = str + "2E";
                break;
        }
        switch (cph.substring(4, 5)) {
            case "A":
                str = str + "39";
                break;

            case "B":
                str = str + "D0";
                break;

            case "C":
                str = str + "56";
                break;

            case "D":
                str = str + "28";
                break;

            case "E":
                str = str + "79";
                break;

            case "F":
                str = str + "13";
                break;

            case "G":
                str = str + "F1";
                break;

            case "H":
                str = str + "64";
                break;

            case "J":
                str = str + "9C";
                break;

            case "K":
                str = str + "D3";
                break;

            case "L":
                str = str + "30";
                break;

            case "M":
                str = str + "43";
                break;

            case "N":
                str = str + "27";
                break;

            case "P":
                str = str + "09";
                break;

            case "Q":
                str = str + "EB";
                break;

            case "R":
                str = str + "87";
                break;

            case "S":
                str = str + "78";
                break;

            case "T":
                str = str + "92";
                break;

            case "U":
                str = str + "57";
                break;

            case "V":
                str = str + "AF";
                break;

            case "W":
                str = str + "2F";
                break;

            case "X":
                str = str + "01";
                break;

            case "Y":
                str = str + "1F";
                break;

            case "Z":
                str = str + "62";
                break;

            case "0":
                str = str + "0E";
                break;

            case "1":
                str = str + "97";
                break;

            case "2":
                str = str + "5F";
                break;

            case "3":
                str = str + "0B";
                break;

            case "4":
                str = str + "75";
                break;

            case "5":
                str = str + "E4";
                break;

            case "6":
                str = str + "C4";
                break;

            case "7":
                str = str + "C3";
                break;

            case "8":
                str = str + "82";
                break;

            case "9":
                str = str + "18";
                break;
        }
        switch (cph.substring(3, 4)) {
            case "A":
                str = str + "41";
                break;

            case "B":
                str = str + "A8";
                break;

            case "C":
                str = str + "2E";
                break;

            case "D":
                str = str + "50";
                break;

            case "E":
                str = str + "01";
                break;

            case "F":
                str = str + "6B";
                break;

            case "G":
                str = str + "89";
                break;

            case "H":
                str = str + "1C";
                break;

            case "J":
                str = str + "E4";
                break;

            case "K":
                str = str + "AB";
                break;

            case "L":
                str = str + "48";
                break;

            case "M":
                str = str + "3B";
                break;

            case "N":
                str = str + "5F";
                break;

            case "P":
                str = str + "71";
                break;

            case "Q":
                str = str + "93";
                break;

            case "R":
                str = str + "FF";
                break;

            case "S":
                str = str + "00";
                break;

            case "T":
                str = str + "EA";
                break;

            case "U":
                str = str + "2F";
                break;

            case "V":
                str = str + "D7";
                break;

            case "W":
                str = str + "57";
                break;

            case "X":
                str = str + "79";
                break;

            case "Y":
                str = str + "67";
                break;

            case "Z":
                str = str + "1A";
                break;

            case "0":
                str = str + "76";
                break;

            case "1":
                str = str + "EF";
                break;

            case "2":
                str = str + "27";
                break;

            case "3":
                str = str + "73";
                break;

            case "4":
                str = str + "0D";
                break;

            case "5":
                str = str + "9C";
                break;

            case "6":
                str = str + "BC";
                break;

            case "7":
                str = str + "BB";
                break;

            case "8":
                str = str + "FA";
                break;

            case "9":
                str = str + "60";
                break;
        }
        switch (cph.substring(2, 3)) {
            case "A":
                str = str + "E1";
                break;

            case "B":
                str = str + "08";
                break;

            case "C":
                str = str + "8E";
                break;

            case "D":
                str = str + "F0";
                break;

            case "E":
                str = str + "A1";
                break;

            case "F":
                str = str + "CB";
                break;

            case "G":
                str = str + "29";
                break;

            case "H":
                str = str + "BC";
                break;

            case "J":
                str = str + "44";
                break;

            case "K":
                str = str + "0B";
                break;

            case "L":
                str = str + "E8";
                break;

            case "M":
                str = str + "9B";
                break;

            case "N":
                str = str + "FF";
                break;

            case "P":
                str = str + "D1";
                break;

            case "Q":
                str = str + "33";
                break;

            case "R":
                str = str + "5F";
                break;

            case "S":
                str = str + "A0";
                break;

            case "T":
                str = str + "4A";
                break;

            case "U":
                str = str + "8F";
                break;

            case "V":
                str = str + "77";
                break;

            case "W":
                str = str + "F7";
                break;

            case "X":
                str = str + "D9";
                break;

            case "Y":
                str = str + "C7";
                break;

            case "Z":
                str = str + "BA";
                break;

            case "0":
                str = str + "D6";
                break;

            case "1":
                str = str + "4F";
                break;

            case "2":
                str = str + "87";
                break;

            case "3":
                str = str + "D3";
                break;

            case "4":
                str = str + "AD";
                break;

            case "5":
                str = str + "3C";
                break;

            case "6":
                str = str + "1C";
                break;

            case "7":
                str = str + "1B";
                break;

            case "8":
                str = str + "5A";
                break;

            case "9":
                str = str + "C0";
                break;
        }
        switch (cph.substring(1, 2)) {
            case "A":
                str = str + "D0";
                break;

            case "B":
                str = str + "39";
                break;

            case "C":
                str = str + "BF";
                break;

            case "D":
                str = str + "C1";
                break;

            case "E":
                str = str + "90";
                break;

            case "F":
                str = str + "FA";
                break;

            case "G":
                str = str + "18";
                break;

            case "H":
                str = str + "8D";
                break;

            case "J":
                str = str + "75";
                break;

            case "K":
                str = str + "3A";
                break;

            case "L":
                str = str + "D9";
                break;

            case "M":
                str = str + "AA";
                break;

            case "N":
                str = str + "CE";
                break;

            case "P":
                str = str + "E0";
                break;

            case "Q":
                str = str + "02";
                break;

            case "R":
                str = str + "6E";
                break;

            case "S":
                str = str + "91";
                break;

            case "T":
                str = str + "7B";
                break;

            case "U":
                str = str + "BE";
                break;

            case "V":
                str = str + "46";
                break;

            case "W":
                str = str + "C6";
                break;

            case "X":
                str = str + "E8";
                break;

            case "Y":
                str = str + "F6";
                break;

            case "Z":
                str = str + "8B";
                break;

            case "0":
                str = str + "E7";
                break;

            case "1":
                str = str + "7E";
                break;

            case "2":
                str = str + "B6";
                break;

            case "3":
                str = str + "E2";
                break;

            case "4":
                str = str + "9C";
                break;

            case "5":
                str = str + "0D";
                break;

            case "6":
                str = str + "2D";
                break;

            case "7":
                str = str + "2A";
                break;

            case "8":
                str = str + "6B";
                break;

            case "9":
                str = str + "F1";
                break;
        }
        switch (cph.substring(0, 1)) {
            case "A":
                return (str + "BE");

            case "B":
                return (str + "57");

            case "C":
                return (str + "D1");

            case "D":
                return (str + "AF");

            case "E":
                return (str + "FE");

            case "F":
                return (str + "94");

            case "G":
                return (str + "76");

            case "H":
                return (str + "E3");

            case "J":
                return (str + "1B");

            case "K":
                return (str + "54");

            case "L":
                return (str + "B7");

            case "M":
                return (str + "C4");

            case "N":
                return (str + "A0");

            case "P":
                return (str + "8E");

            case "Q":
                return (str + "6C");

            case "R":
                return (str + "00");

            case "S":
                return (str + "FF");

            case "T":
                return (str + "15");

            case "U":
                return (str + "D0");

            case "V":
                return (str + "28");

            case "W":
                return (str + "A8");

            case "X":
                return (str + "86");

            case "Y":
                return (str + "98");

            case "Z":
                return (str + "E5");
        }
        return str;
    }
    
    public static void main(String[] args){
        System.out.println(Encription.Encryption("C2C003"));
        System.out.println(Encription.Encryption("C0C222"));
        System.out.println(Encription.Encryption("C2C005"));
    }
}
