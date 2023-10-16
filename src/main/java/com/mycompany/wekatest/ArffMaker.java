package com.mycompany.wekatest;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;


import com.tictactec.ta.lib.Core;
import com.tictactec.ta.lib.MInteger;

public class ArffMaker {

    public void makeArff() {
        int daysAhead = 5;
        try {
            PrintWriter pw = new PrintWriter("test.arff");

            pw.println("@relation covid");
            pw.append("<br/>");

            pw.println("@attribute NumeroMorador numeric");
            pw.println("@attribute Rendamédia2020IPCA numeric");
            pw.println("@attribute Sexo numeric");
            pw.println("@attribute Idade numeric");
            pw.println("@attribute FrequentouEscola numeric");
            pw.println("@attribute GrauEnsino numeric");
            pw.println("@attribute ConcluiuSuperior numeric");
            pw.println("@attribute GrauEnsinoMoradorMaisEstudou numeric");
            pw.println("@attribute CorRaca numeric");
            pw.println("@attribute ProcurouServicoSaudeQuinzeDias numeric");
            pw.println("@attribute TipoServicoSaude numeric");
            pw.println("@attribute CumprindoIsolamento numeric");
            pw.println("@attribute MotivoProcuraServico numeric");
            pw.println("@attribute RotinaAtividades numeric");
            pw.println("@attribute RotinaCasa numeric");
            pw.println("@attribute ValorQualitativo {0,1}");

            pw.append("<br/>");
            pw.println("@data");

            pw.flush();

            BufferedReader br = new BufferedReader(new FileReader("test.csv"));
            br.readLine(); // pula os headers

            ArrayList closes = new ArrayList<>();
            String in = "";
            while ((in = br.readLine()) != null) {
                String ins[] = in.split(",");
                closes.add(Double.parseDouble(ins[5]));
            }
            br.close();

            double[] dclose = new double[closes.size()];
            for (int ix = 0; ix < closes.size(); ix++) {
                dclose[ix] = (double) closes.get(ix);
            }

            Core core = new Core();
            MInteger outBegIdx = new MInteger();
            MInteger outNBElement = new MInteger();
            double[] smaFast = new double[closes.size()];
            core.sma(0, dclose.length - 1, dclose, 5, outBegIdx, outNBElement, smaFast);

            for (int ix = smaFast.length - 1; ix > 5; ix--) {
                smaFast[ix] = smaFast[ix - 5];
            }

            outBegIdx = new MInteger();
            outNBElement = new MInteger();
            double[] smaSlow = new double[closes.size()];
            core.sma(0, dclose.length - 1, dclose, 20, outBegIdx, outNBElement, smaSlow);
            for (int ix = smaSlow.length - 1; ix > 20; ix--) {
                smaSlow[ix] = smaSlow[ix - 20];
            }

            for (int ix = 25; ix < dclose.length - daysAhead; ix++) {
                pw.println(dclose[ix] + "," + smaFast[ix] + "," + smaSlow[ix] + "," + dclose[ix + daysAhead]);
            }

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }

}
