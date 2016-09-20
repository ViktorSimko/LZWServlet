package com.viktorsimko.lzwservlet;

// Copyright (C) 2011, 2012, Bátfai Norbert, nbatfai@inf.unideb.hu, nbatfai@gmail.com
//
// This program is free software: you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with this program.  If not, see <http://www.gnu.org/licenses/>.

class LZWBinFa {
    LZWBinFa() {
        fa = gyoker;
    }

    void egyBitFeldolg(char b) {
        if (b == '0') {
            if (fa.nullasGyermek() == null)
            {
                Csomopont uj = new Csomopont('0');
                fa.ujNullasGyermek(uj);
                fa = gyoker;
            } else
            {
                fa = fa.nullasGyermek();
            }
        }
        else {
            if (fa.egyesGyermek() == null) {
                Csomopont uj = new Csomopont('1');
                fa.ujEgyesGyermek(uj);
                fa = gyoker;
            } else {
                fa = fa.egyesGyermek();
            }
        }
    }

    void kiir(java.io.PrintWriter os) {
        melyseg = 0;
        kiir(gyoker, os);
    }

    private class Csomopont {
        Csomopont(char betu) {
            this.betu = betu;
            balNulla = null;
            jobbEgy = null;
        }

        Csomopont nullasGyermek() {
            return balNulla;
        }


        Csomopont egyesGyermek() {
            return jobbEgy;
        }

        void ujNullasGyermek(Csomopont gy) {
            balNulla = gy;
        }

        void ujEgyesGyermek(Csomopont gy) {
            jobbEgy = gy;
        }

        char getBetu() {
            return betu;
        }

        private char betu;
        private Csomopont balNulla = null;
        private Csomopont jobbEgy = null;
    }

    private Csomopont fa = null;
    private int melyseg, atlagosszeg, atlagdb;
    private double szorasosszeg;

    private void kiir(Csomopont elem, java.io.PrintWriter os) {

        if (elem != null) {
            ++melyseg;
            kiir(elem.egyesGyermek(), os);
            // ez a postorder bejÃ¡rÃ¡shoz kÃ©pest
            // 1-el nagyobb mÃ©lysÃ©g, ezÃ©rt -1
            for (int i = 0; i < melyseg; ++i) {
                os.print("---");
            }
            os.print(elem.getBetu());
            os.print("(");
            os.print(melyseg - 1);
            os.print(")<br/>");
            kiir(elem.nullasGyermek(), os);
            --melyseg;
        }
    }

    private Csomopont gyoker = new Csomopont('/');
    private int maxMelyseg;
    private double atlag;

    int getMelyseg() {
        melyseg = maxMelyseg = 0;
        rmelyseg(gyoker);
        return maxMelyseg - 1;
    }

    double getAtlag() {
        melyseg = atlagosszeg = atlagdb = 0;
        ratlag(gyoker);
        atlag = ((double) atlagosszeg) / atlagdb;
        return atlag;
    }

    double getSzoras() {
        atlag = getAtlag();
        szorasosszeg = 0.0;
        melyseg = atlagdb = 0;

        rszoras(gyoker);

        double szoras;
        if (atlagdb - 1 > 0) {
            szoras = Math.sqrt(szorasosszeg / (atlagdb - 1));
        } else {
            szoras = Math.sqrt(szorasosszeg);
        }

        return szoras;
    }

    private void rmelyseg(Csomopont elem) {
        if (elem != null) {
            ++melyseg;
            if (melyseg > maxMelyseg) {
                maxMelyseg = melyseg;
            }
            rmelyseg(elem.egyesGyermek());
            rmelyseg(elem.nullasGyermek());
            --melyseg;
        }
    }

    private void ratlag(Csomopont elem) {
        if (elem != null) {
            ++melyseg;
            ratlag(elem.egyesGyermek());
            ratlag(elem.nullasGyermek());
            --melyseg;
            if (elem.egyesGyermek() == null && elem.nullasGyermek() == null) {
                ++atlagdb;
                atlagosszeg += melyseg;
            }
        }
    }

    private void rszoras(Csomopont elem) {
        if (elem != null) {
            ++melyseg;
            rszoras(elem.egyesGyermek());
            rszoras(elem.nullasGyermek());
            --melyseg;
            if (elem.egyesGyermek() == null && elem.nullasGyermek() == null) {
                ++atlagdb;
                szorasosszeg += ((melyseg - atlag) * (melyseg - atlag));
            }
        }
    }
}
