package com.example.ivancallisaya.impresionpedidofacil.ui.auxiliars;

public class LibTab {

    public static String ReplacesCharsTo(String sLinea)
    {
        sLinea = sLinea.replace('Á', 'A');
        sLinea = sLinea.replace('É', 'E');
        sLinea = sLinea.replace('Í', 'I');
        sLinea = sLinea.replace('Ó', 'O');
        sLinea = sLinea.replace('Ú', 'U');

        sLinea = sLinea.replace('á', 'a');
        sLinea = sLinea.replace('é', 'e');
        sLinea = sLinea.replace('í', 'i');
        sLinea = sLinea.replace('ó', 'o');
        sLinea = sLinea.replace('ú', 'u');

        sLinea = sLinea.replace('Ñ', 'N');
        sLinea = sLinea.replace('ñ', 'n');
        sLinea = sLinea.replace('´', '`');


        return sLinea;
    }

    public static int LineCount(String sParrafo, int CountChar)
    {
        if (sParrafo.trim().length() > 0)
        {
            int nLen = sParrafo.length() / CountChar;
            int nRes = sParrafo.length() % CountChar;
            if (nRes > 0)
            {
                nLen++;
            }
            return nLen;
        }
        else
            return 0;
    }

    public static  String[] LinesParrafo(String sParrafo, int CountChar)
    {
        String[] sTokens = sParrafo.trim().split(" ");
        String sToken = "";
        int nLen = LineCount(sParrafo, CountChar);
        if (nLen > 0)
        {
            String[] sLineas = new String[nLen];
            String sLinea = "";
            int nIndex = -1;
            for (String word :sTokens)
            {
                sToken = word;
                if (sLinea.length() + sToken.length()<= CountChar)
                {
                    sLinea = sLinea + sToken + " ";
                }
                else
                {
                    nIndex++;
                    sLineas[nIndex] = sLinea;
                    sLinea = sToken.trim() + " ";
                }
            }
            if (sLinea.length() > 0)
            {
                nIndex++;
                sLineas[nIndex] = sLinea;
            }
            else if (sToken.length()> 0)
            {
                nIndex++;
                sLineas[nIndex] = sToken;
            }
            return sLineas;
        }
        else
            return sTokens;
    }


}
