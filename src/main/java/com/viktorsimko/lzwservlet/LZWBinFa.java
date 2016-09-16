package com.viktorsimko.lzwservlet;
// LZWBinFa.java
//
// z3a7.cpp to (Z3A7.java) LZWBinFa.java, a z3a7.cpp mechanikus Ã¡tÃ­rata Java-ba.
//
// EgyÃ¼tt tÃ¡madjuk meg: http://progpater.blog.hu/2011/04/14/egyutt_tamadjuk_meg
// LZW fa Ã©pÃ­tÅ‘ 3. C++ Ã¡tirata a C valtozatbol (+mÃ©lysÃ©g, atlag Ã©s szÃ³rÃ¡s)
// ProgramozÃ³ PÃ¡ternoszter
//
// Copyright (C) 2011, 2012, BÃ¡tfai Norbert, nbatfai@inf.unideb.hu, nbatfai@gmail.com
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
//
// Ez a program szabad szoftver; terjeszthetÃµ illetve mÃ³dosÃ­thatÃ³ a
// Free Software Foundation Ã¡ltal kiadott GNU General Public License
// dokumentumÃ¡ban leÃ­rtak; akÃ¡r a licenc 3-as, akÃ¡r (tetszÃµleges) kÃ©sÃµbbi
// vÃ¡ltozata szerint.
//
// Ez a program abban a remÃ©nyben kerÃ¼l kÃ¶zreadÃ¡sra, hogy hasznos lesz,
// de minden egyÃ©b GARANCIA NÃ‰LKÃœL, az ELADHATÃ“SÃGRA vagy VALAMELY CÃ‰LRA
// VALÃ“ ALKALMAZHATÃ“SÃGRA valÃ³ szÃ¡rmaztatott garanciÃ¡t is beleÃ©rtve.
// TovÃ¡bbi rÃ©szleteket a GNU General Public License tartalmaz.
//
// A felhasznÃ¡lÃ³nak a programmal egyÃ¼tt meg kell kapnia a GNU General
// Public License egy pÃ©ldÃ¡nyÃ¡t; ha mÃ©gsem kapta meg, akkor
// tekintse meg a <http://www.gnu.org/licenses/> oldalon.
//
//
// Version history:
//
// 0.0.1,       http://progpater.blog.hu/2011/02/19/gyonyor_a_tomor
// 0.0.2,       csomÃ³pontok mutatÃ³inak NULLÃ¡zÃ¡sa (nem fejtette meg senki :)
// 0.0.3,       http://progpater.blog.hu/2011/03/05/labormeres_otthon_avagy_hogyan_dolgozok_fel_egy_pedat
// 0.0.4,       z.cpp: a C verziÃ³bÃ³l svn: bevezetes/C/ziv/z.c Ã¡tÃ­rjuk C++-ra
//              http://progpater.blog.hu/2011/03/31/imadni_fogjatok_a_c_t_egy_emberkent_tiszta_szivbol
// 0.0.5,       z2.cpp: az fgv(*mut)-ok helyett fgv(&ref)
// 0.0.6,       z3.cpp: Csomopont beÃ¡gyazva
//              http://progpater.blog.hu/2011/04/01/imadni_fogjak_a_c_t_egy_emberkent_tiszta_szivbol_2
// 0.0.6.1      z3a2.c: LZWBinFa mÃ¡r nem barÃ¡tja a Csomopont-nak, mert annak tagjait nem hasznÃ¡lja direktben
// 0.0.6.2      Kis kommentezÃ©st teszÃ¼nk bele 1. lÃ©pÃ©skÃ©nt (hogy a kicsit lemaradt hallgatÃ³knak is
//              kÃ¶nnyebb legyen, jÃ³l megtÅ±zdeljÃ¼k tovÃ¡bbi olvasmÃ¡nyokkal)
//              http://progpater.blog.hu/2011/04/14/egyutt_tamadjuk_meg
//              (majd a 2. lÃ©pÃ©sben "beletesszÃ¼k a d.c-t", majd s 3. lÃ©pÃ©sben a parancssorsor argok feldolgozÃ¡sÃ¡t)
// 0.0.6.3      z3a2.c: FejlesztgetjÃ¼k a forrÃ¡st: http://progpater.blog.hu/2011/04/17/a_tizedik_tizenegyedik_labor
// 0.0.6.4      SVN-beli, http://www.inf.unideb.hu/~nbatfai/p1/forrasok-SVN/bevezetes/vedes/
// 0.0.6.5      2012.03.20, z3a4.cpp: N betÅ±k (hiÃ¡nyok), sorvÃ©gek, vezetÅ‘ komment figyelmen kÃ­vÃ¼l: http://progpater.blog.hu/2012/03/20/a_vedes_elokeszitese
// 0.0.6.6      z3a5.cpp: mamenyaka kollÃ©ga Ã©szrevÃ©telÃ©re a tÃ¶bb komment sor figyelmen kÃ­vÃ¼l hagyÃ¡sa
//		http://progpater.blog.hu/2012/03/20/a_vedes_elokeszitese/fullcommentlist/1#c16150365
// 0.0.6.7	Javaslom ezt a verziÃ³t vÃ¡lasztani vÃ©dendÅ‘ programnak
// 0.0.6.8	z3a7.cpp: pÃ¡r kisebb javÃ­tÃ¡s, illetve a vÃ©dÃ©sek tÃ¡mogatÃ¡sÃ¡hoz tovÃ¡bbi komment a <<
// 		eltolÃ³ operÃ¡tort tagfÃ¼ggvÃ©nykÃ©nt, illetve globÃ¡lis fÃ¼ggvÃ©nykÃ©nt tÃºlterhelÅ‘ rÃ©szekhez.
//		http://progpater.blog.hu/2012/04/10/imadni_fogjak_a_c_t_egy_emberkent_tiszta_szivbol_4/fullcommentlist/1#c16341099
// prog2-re,    z3a7.cpp to Z3A7.java, teljesen mechanikus Ã¡tÃ­rÃ¡s, nÃ©hÃ¡ny
//              soronkÃ©nt a C++ kÃ³dot betettem kommentbe Ã©s utÃ¡na leÃ­rtam
//              ugyanazt a nÃ©hÃ¡ny sort JavÃ¡ban. Ez tehÃ¡t nem egy tiszta Java
//              implementÃ¡ciÃ³, from scratch, hanem egy szinte soronkÃ©nti Ã¡tÃ­rat,
//              de Ã©ppen ezÃ©rt Ã©rdekes Ã¶sszevetni a C++ Ã©s a Java leÃ­rÃ¡st.
//              Ennek megfelelÅ‘en a Java progi OO szerkezete ua., mint a C++
//              vÃ¡ltozat volt, a lÃ©nyegi mÅ±kÃ¶dÃ©st tekintve is helyesek a
//              kommentek (de a JavÃ¡ban kimaradt a << operÃ¡tor tÃºlterhelÃ©se, a
//              destruktorok stb. termÃ©szetesen).
//

/*
 * #include <iostream>	// mert olvassuk a std::cin, Ã­rjuk a std::cout
 * csatornÃ¡kat #include <cmath>	// mert vonunk gyÃ¶kÃ¶t a szÃ³rÃ¡shoz: std::sqrt
 * #include <fstream>	// fÃ¡jlbÃ³l olvasunk, Ã­runk majd
 */

/*
 * Az LZWBinFa osztÃ¡lyban absztrahÃ¡ljuk az LZW algoritmus binÃ¡ris fa Ã©pÃ­tÃ©sÃ©t.
 * Az osztÃ¡ly definÃ­ciÃ³jÃ¡ba beÃ¡gyazzuk a fa egy csomÃ³pontjÃ¡nak az absztrakt
 * jellemzÃ©sÃ©t, ez lesz a beÃ¡gyazott Csomopont osztÃ¡ly. MiÃ©rt Ã¡gyazzuk be? Mert
 * kÃ¼lÃ¶n nem szÃ¡nunk neki szerepet, ezzel is jelezzÃ¼k, hogy csak a fa rÃ©szekÃ©nt
 * szÃ¡miolunk vele.
 */

/*
 * class LZWBinFa {
 */
public class LZWBinFa {
  /*
   * public:
   *
   */
  /*
   * Szemben a binÃ¡ris keresÅ‘fÃ¡nkkal (BinFa osztÃ¡ly)
   * http://progpater.blog.hu/2011/04/12/imadni_fogjak_a_c_t_egy_emberkent_tiszta_szivbol_3
   * itt (LZWBinFa osztÃ¡ly) a fa gyÃ¶kere nem pointer, hanem a '/' betÃ¼t
   * tartalmazÃ³ objektum, lÃ¡sd majd a vÃ©dett tagok kÃ¶zÃ¶tt lent: Csomopont gyoker;
   * A fa viszont mÃ¡r pointer, mindig az Ã©pÃ¼lÅ‘ LZW-fÃ¡nk azon csomÃ³pontjÃ¡ra mutat,
   * amit az input feldolgozÃ¡sa sorÃ¡n az LZW algoritmus logikÃ¡ja diktÃ¡l:
   * http://progpater.blog.hu/2011/02/19/gyonyor_a_tomor Ez a konstruktor annyit
   * csinÃ¡l, hogy a fa mutatÃ³t rÃ¡Ã¡llÃ­tja a gyÃ¶kÃ©rre. (Mert ugye laboron, blogon,
   * elÅ‘adÃ¡sban tisztÃ¡ztuk, hogy a tartalmazott tagok, most "Csomopont gyoker"
   * konstruktora elÅ‘bb lefut, mint a tagot tartalmazÃ³ LZWBinFa osztÃ¡ly
   * konstruktora, Ã©ppen a kÃ¶vetkezÅ‘, azaz a fa=&gyoker OK.)
   */
  /*
   * LZWBinFa ():fa (&gyoker) { } ~LZWBinFa () { szabadit (gyoker.egyesGyermek
   * ()); szabadit (gyoker.nullasGyermek ()); }
   */

    public LZWBinFa() {

        fa = gyoker;

    }
  /* TagfÃ¼ggvÃ©nykÃ©nt tÃºlterheljÃ¼k a << operÃ¡tort, ezzel a cÃ©lunk, hogy felkeltsÃ¼k a
     hallgatÃ³ Ã©rdeklÅ‘dÃ©sÃ©t, mert ekkor Ã­gy nyomhatjuk a fÃ¡ba az inputot: binFa << b; ahol a b
     egy '0' vagy '1'-es betÅ±.
     Mivel tagfÃ¼ggvÃ©ny, Ã­gy van rÃ¡ "Ã©rtelmezve" az aktuÃ¡lis (this "rejtett paramÃ©terkÃ©nt"
     kapott ) pÃ©ldÃ¡ny, azaz annak a fÃ¡nak amibe Ã©ppen be akarjuk nyomni a b betÅ±t a tagjai
     (pl.: "fa", "gyoker") hasznÃ¡lhatÃ³ak a fÃ¼ggvÃ©nyben.

     A fÃ¼ggvÃ©nybe programoztuk az LZW fa Ã©pÃ­tÃ©sÃ©nek algoritmusÃ¡t tk.:
     http://progpater.blog.hu/2011/02/19/gyonyor_a_tomor

     a b formÃ¡lis param az a betÅ±, amit Ã©ppen be kell nyomni a fÃ¡ba.

     a binFa << b (ahol a b majd a vÃ©gÃ©n lÃ¡tszik, hogy mÃ¡r az '1' vagy a '0') azt jelenti
     tagfÃ¼ggvÃ©nykÃ©nt, hogy binFa.operator<<(b) (globÃ¡liskÃ©nt Ã­gy festene: operator<<(binFa, b) )

     */
  /*
  void operator<< (char b)
  {
    // Mit kell betenni Ã©ppen, '0'-t?
    if (b == '0')
      {
      *
      */

    public void egyBitFeldolg(char b) {
        // Mit kell betenni Ã©ppen, '0'-t?
        if (b == '0') {
      /*
       * Van '0'-s gyermeke az aktuÃ¡lis csomÃ³pontnak? megkÃ©rdezzÃ¼k TÅ‘le, a "fa"
       * mutatÃ³ Ã©ppen reÃ¡ mutat
       */
      /*
       * if (!fa->nullasGyermek ())	// ha nincs, hÃ¡t akkor csinÃ¡lunk { //
       * elkÃ©szÃ­tjÃ¼k, azaz pÃ¡ldÃ¡nyosÃ­tunk a '0' betÅ± akt. parammal Csomopont *uj
       * = new Csomopont ('0'); // az aktuÃ¡lis csomÃ³pontnak, ahol Ã¡llunk azt
       * Ã¼zenjÃ¼k, hogy // jegyezze mÃ¡r be magÃ¡nak, hogy nullÃ¡s gyereke mostantÃ³l
       * van // kÃ¼ldjÃ¼k is Neki a gyerek cÃ­mÃ©t: fa->ujNullasGyermek (uj); // Ã©s
       * visszaÃ¡llunk a gyÃ¶kÃ©rre (mert ezt diktÃ¡lja az alg.) fa = &gyoker; } else
       * // ha van, arra rÃ¡lÃ©pÃ¼nk { // azaz a "fa" pointer mÃ¡r majd a szÃ³ban
       * forgÃ³ gyermekre mutat: fa = fa->nullasGyermek (); } }
       *
       */
            if (fa.nullasGyermek() == null) // ha nincs, hÃ¡t akkor csinÃ¡lunk
            {
                // elkÃ©szÃ­tjÃ¼k, azaz pÃ¡ldÃ¡nyosÃ­tunk a '0' betÅ± akt. parammal
                Csomopont uj = new Csomopont('0');
                // az aktuÃ¡lis csomÃ³pontnak, ahol Ã¡llunk azt Ã¼zenjÃ¼k, hogy
                // jegyezze mÃ¡r be magÃ¡nak, hogy nullÃ¡s gyereke mostantÃ³l van
                // kÃ¼ldjÃ¼k is Neki a gyerek cÃ­mÃ©t:
                fa.ujNullasGyermek(uj);
                // Ã©s visszaÃ¡llunk a gyÃ¶kÃ©rre (mert ezt diktÃ¡lja az alg.)
                fa = gyoker;
            } else // ha van, arra rÃ¡lÃ©pÃ¼nk
            {
                // azaz a "fa" pointer mÃ¡r majd a szÃ³ban forgÃ³ gyermekre mutat:
                fa = fa.nullasGyermek();
            }
        } // Mit kell betenni Ã©ppen, vagy '1'-et?
    /*
     * else { if (!fa->egyesGyermek ()) { Csomopont *uj = new Csomopont ('1');
     * fa->ujEgyesGyermek (uj); fa = &gyoker; } else { fa = fa->egyesGyermek ();
     * } } }
     *
     */ else {
            if (fa.egyesGyermek() == null) {
                Csomopont uj = new Csomopont('1');
                fa.ujEgyesGyermek(uj);
                fa = gyoker;
            } else {
                fa = fa.egyesGyermek();
            }
        }
    }
  /* A bejÃ¡rÃ¡ssal kapcsolatos fÃ¼ggvÃ©nyeink (tÃºlterhelt kiir-Ã³k, atlag, ratlag stb.) rekurzÃ­vak,
     tk. a rekurzÃ­v fabejÃ¡rÃ¡st valÃ³sÃ­tjÃ¡k meg (lÃ¡sd a 3. elÅ‘adÃ¡s "FabejÃ¡rÃ¡s" c. fÃ³liÃ¡jÃ¡t Ã©s tÃ¡rsait)

     (Ha a rekurzÃ­v fÃ¼ggvÃ©nnyel Ã¡ltalÃ¡ban gondod van => K&R kÃ¶nyv megfelelÅ‘ rÃ©sze: a 3. ea. izometrikus
     rÃ©szÃ©ben ezt "letÃ¡ncoltuk" :) Ã©s kÃ¼lÃ¶n idÃ©ztÃ¼k a K&R Ã¡llÃ¡spontjÃ¡t :)
   */
  /*
  void kiir (void)
  {
  *
  */

    public void kiir() {
        // Sokkal elegÃ¡nsabb lenne (Ã©s mÃ¡s, a bevezetÃ©sben nem kibontandÃ³ reentrÃ¡ns kÃ©rdÃ©sek miatt is, mert
        // ugye ha most kÃ©t helyrÅ‘l hÃ­vjÃ¡k meg az objektum ilyen fÃ¼ggvÃ©nyeit, tahÃ¡t ha kÃ©tszer kezd futni az
        // objektum kiir() fgv.-e pl., az komoly hiba, mert elromlana a mÃ©lysÃ©g... tehÃ¡t a mostani megoldÃ¡sunk
        // nem reentrÃ¡ns) ha nem hasznÃ¡lnÃ¡nk a C verziÃ³ban globÃ¡lis vÃ¡ltozÃ³kat, a C++ vÃ¡ltozatban pÃ©ldÃ¡nytagot a
        // mÃ©lysÃ©g kezelÃ©sÃ©re: http://progpater.blog.hu/2011/03/05/there_is_no_spoon
        melyseg = 0;
        // ha nem mondta meg a hÃ­vÃ³ az Ã¼zenetben, hogy hova Ã­rjuk ki a fÃ¡t, akkor a
        // sztenderd out-ra nyomjuk
    /*
     * kiir (&gyoker, std::cout);
     *
     */
        kiir(gyoker, new java.io.PrintWriter(System.out));

    }
  /* mÃ¡r nem hasznÃ¡ljuk, tartalmÃ¡t a dtor hÃ­vja
  void szabadit (void)
  {
    szabadit (gyoker.egyesGyermek ());
    szabadit (gyoker.nullasGyermek ());
    // magÃ¡t a gyÃ¶keret nem szabadÃ­tjuk, hiszen azt nem mi foglaltuk a szabad tÃ¡rban (halmon).
  }
  */

    /* A vÃ¡ltozatossÃ¡g kedvÃ©Ã©rt ezeket az osztÃ¡lydefinÃ­ciÃ³ (class LZWBinFa {...};) utÃ¡n definiÃ¡ljuk,
       hogy kÃ©nytelen lÃ©gy az LZWBinFa Ã©s a :: hatÃ³kÃ¶r operÃ¡torral minÅ‘sÃ­tve definiÃ¡lni :) l. lentebb */
  /*
  int getMelyseg (void);
  double getAtlag (void);
  double getSzoras (void);
  */
  /* VÃ¡gyunk, hogy a felÃ©pÃ­tett LZW fÃ¡t ki tudjuk nyomni ilyenformÃ¡n: std::cout << binFa;
     de mivel a << operÃ¡tor is a sztenderd nÃ©vtÃ©rben van, de a using namespace std-t elvbÅ‘l
     nem hasznÃ¡ljuk bevezetÅ‘ kurzusban, Ã­gy ez a konstrukciÃ³ csak az argfÃ¼ggÅ‘ nÃ©vfeloldÃ¡s miatt
     fordul le (B&L kÃ¶nyv 185. o. teteje) Ã¡m itt nem az a lÃ©nyeg, hanem, hogy a cout ostream
     osztÃ¡lybeli, Ã­gy abban az osztÃ¡lyban kÃ©ne mÃ³dosÃ­tani, hogy tudjon kiÃ­rni LZWBinFa osztÃ¡lybelieket...
     e helyett a globÃ¡lis << operÃ¡tort terheljÃ¼k tÃºl,

     a kiFile << binFa azt jelenti, hogy

      - tagfÃ¼ggvÃ©nykÃ©nt: kiFile.operator<<(binFa) de ehhez a kiFile valamilyen
      std::ostream stream osztÃ¡ly forrÃ¡sÃ¡ba kellene beleÃ­rni ezt a tagfÃ¼ggvÃ©nyt,
      amely ismeri a mi LZW binfÃ¡nkat...

      - globÃ¡lis fÃ¼ggvÃ©nykÃ©nt: operator<<(kiFile, binFa) Ã©s pont ez lÃ¡tszik a kÃ¶vetkezÅ‘ sorban:

     */
  /*
  friend std::ostream & operator<< (std::ostream & os, LZWBinFa & bf)
  {
    bf.kiir (os);
    return os;
  }
  void kiir (std::ostream & os)
  {
    melyseg = 0;
    kiir (&gyoker, os);
  }
  *
  */
    public void kiir(java.io.PrintWriter os) {
        melyseg = 0;
        kiir(gyoker, os);
    }

    /*
  private:
    class Csomopont
    {
    public:
    *
    */
    class Csomopont {

        /*
         * A paramÃ©ter nÃ©lkÃ¼li konstruktor az elepÃ©rtelmezett '/' "gyÃ¶kÃ©r-betÅ±vel"
         * hozza lÃ©tre a csomÃ³pontot, ilyet hÃ­vunk a fÃ¡bÃ³l, aki tagkÃ©nt tartalmazza a
         * gyÃ¶keret. MÃ¡skÃ¼lÃ¶nben, ha valami betÅ±vel hÃ­vjuk, akkor azt teszi a "betu"
         * tagba, a kÃ©t gyermekre mutatÃ³ mutatÃ³t pedig nullra Ã¡llÃ­tjuk, C++-ban a 0
         * is megteszi.
         */
    /*
     * Csomopont (char b = '/'):betu (b), balNulla (0), jobbEgy (0) { };
     * ~Csomopont () { };
     *
     */
        public Csomopont(char betu) {
            this.betu = betu;
            balNulla = null;
            jobbEgy = null;
        }

        ;


        // AktuÃ¡lis csomÃ³pont, mondd meg nÃ©kem, ki a bal oldali gyermeked
        // (a C verziÃ³ logikÃ¡jÃ¡val mÅ±xik ez is: ha nincs, akkor a null megy vissza)
  /*
    Csomopont *nullasGyermek () const
    {
      return balNulla;
    }*/
        public Csomopont nullasGyermek() {
            return balNulla;
        }
        // AktuÃ¡lis csomÃ³pon,t mondd meg nÃ©kem, ki a jobb oldali gyermeked?
    /*
    Csomopont *egyesGyermek () const
    {
      return jobbEgy;
    }*/

        public Csomopont egyesGyermek() {
            return jobbEgy;
        }
        // AktuÃ¡lis csomÃ³pont, Ã­mhol legyen a "gy" mutatta csomÃ³pont a bal oldali gyereked!
    /*
    void ujNullasGyermek (Csomopont * gy)
    {
      balNulla = gy;
    }*/

        public void ujNullasGyermek(Csomopont gy) {
            balNulla = gy;
        }
        // AktuÃ¡lis csomÃ³pont, Ã­mhol legyen a "gy" mutatta csomÃ³pont a jobb oldali gyereked!
    /*
    void ujEgyesGyermek (Csomopont * gy)
    {
      jobbEgy = gy;
    }*/

        public void ujEgyesGyermek(Csomopont gy) {
            jobbEgy = gy;
        }
        // AktuÃ¡lis csomÃ³pont: Te milyen betÅ±t hordozol?
        // (a const kulcsszÃ³val jelezzÃ¼k, hogy nem bÃ¡ntjuk a pÃ©ldÃ¡nyt)
    /*
    char getBetu () const
    {
      return betu;
    }*/

        public char getBetu() {
            return betu;
        }
        /*
         * private:
         */
        // friend class LZWBinFa; /* mert ebben a valtozatban az LZWBinFa metÃ³dusai nem kÃ¶zvetlenÃ¼l
        // a Csomopont tagjaival dolgoznak, hanem beÃ¡llÃ­tÃ³/lekÃ©rdezÅ‘ Ã¼zenetekkel Ã©rik el azokat */
        // Milyen betÅ±t hordoz a csomÃ³pont
        private char betu;
        // Melyik mÃ¡sik csomÃ³pont a bal oldali gyermeke? (a C vÃ¡ltozatbÃ³l "Ã¶rÃ¶kÃ¶lt" logika:
        // ha hincs ilyen csermek, akkor balNulla == null) igaz
    /*
     * Csomopont *balNulla; Csomopont *jobbEgy;
     *
     */
        private Csomopont balNulla = null;
        private Csomopont jobbEgy = null;
        // nem mÃ¡solhatÃ³ a csomÃ³pont (Ã¶kÃ¶rszabÃ¡ly: ha van valamilye a szabad tÃ¡rban,
        // letiltjuk a mÃ¡solÃ³ konstruktort, meg a mÃ¡solÃ³ Ã©rtÃ©kadÃ¡st)
    /*
     * Csomopont (const Csomopont &); Csomopont & operator= (const Csomopont &);
     *
     */
    };

    /*
     * Mindig a fa "LZW algoritmus logikÃ¡ja szerinti aktuÃ¡lis" csomÃ³pontjÃ¡ra mutat
     */
  /*
   * Csomopont *fa;
   *
   */
    private Csomopont fa = null;
    // technikai
    private int melyseg, atlagosszeg, atlagdb;
    private double szorasosszeg;
    // szokÃ¡sosan: nocopyable
  /*
  LZWBinFa (const LZWBinFa &);
  LZWBinFa & operator= (const LZWBinFa &);
  *
  */

    /* KiÃ­rja a csomÃ³pontot az os csatornÃ¡ra. A rekurziÃ³ kapcsÃ¡n lÃ¡sd a korÃ¡bbi K&R-es utalÃ¡st... */
  /*
  void kiir (Csomopont * elem, std::ostream & os)
  {
  *
  */
    public void kiir(Csomopont elem, java.io.PrintWriter os) {
        // Nem lÃ©tezÅ‘ csomÃ³ponttal nem foglalkozunk... azaz ez a rekurziÃ³ leÃ¡llÃ­tÃ¡sa
    /*
     * if (elem != NULL) { ++melyseg; kiir (elem->egyesGyermek (), os); // ez a
     * postorder bejÃ¡rÃ¡shoz kÃ©pest // 1-el nagyobb mÃ©lysÃ©g, ezÃ©rt -1 for (int i =
     * 0; i < melyseg; ++i) os << "---"; os << elem->getBetu () << "(" << melyseg
     * - 1 << ")" << std::endl; kiir (elem->nullasGyermek (), os); --melyseg; }
     *
     */
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
  /*
   * void szabadit (Csomopont * elem) { // Nem lÃ©tezÅ‘ csomÃ³ponttal nem
   * foglalkozunk... azaz ez a rekurziÃ³ leÃ¡llÃ­tÃ¡sa if (elem != NULL) { szabadit
   * (elem->egyesGyermek ()); szabadit (elem->nullasGyermek ()); // ha a
   * csomÃ³pont mindkÃ©t gyermekÃ©t felszabadÃ­tottuk // azutÃ¡n szabadÃ­tjuk magÃ¡t a
   * csomÃ³pontot: delete elem; } }
   */
  /*
   * protected:	// ha esetleg egyszer majd kiterjesztjÃ¼k az osztÃ¡lyt, mert
   */
// akarunk benne valami ÃºjdonsÃ¡got csinÃ¡lni, vagy meglÃ©vÅ‘ tevÃ©kenysÃ©get mÃ¡shogy... stb.
// akkor ezek lÃ¡tszanak majd a gyerek osztÃ¡lyban is

    /*
     * A fÃ¡ban tagkÃ©nt benne van egy csomÃ³pont, ez erÅ‘sen ki van tÃ¼ntetve, Å a
     * gyÃ¶kÃ©r:
     */
  /*
   * Csomopont gyoker;
   *
   */
    protected Csomopont gyoker = new Csomopont('/');
    int maxMelyseg;
    double atlag, szoras;

    /*
    void rmelyseg (Csomopont * elem);
    void ratlag (Csomopont * elem);
    void rszoras (Csomopont * elem);
  */
  /*
};
*/
// NÃ©hÃ¡ny fÃ¼ggvÃ©nyt az osztÃ¡lydefinÃ­ciÃ³ utÃ¡n definiÃ¡lunk, hogy lÃ¡ssunk ilyet is ... :)
// Nem erÅ‘ltetjÃ¼k viszont a kÃ¼lÃ¶n fÃ¡jlba szedÃ©st, mert a sablonosztÃ¡lyosÃ­tott tovÃ¡bb
// fejlesztÃ©sben az linkelÃ©si gondot okozna, de ez a tÃ©ma mÃ¡r kivezet a laborteljesÃ­tÃ©s
// szÃ¼ksÃ©ges feladatÃ¡bÃ³l: http://progpater.blog.hu/2011/04/12/imadni_fogjak_a_c_t_egy_emberkent_tiszta_szivbol_3
// EgyÃ©bkÃ©nt a melyseg, atlag Ã©s szoras fgv.-ek a kiir fgv.-el teljesen egy kaptafa.
/*
int
LZWBinFa::getMelyseg (void)
{
  melyseg = maxMelyseg = 0;
  rmelyseg (&gyoker);
  return maxMelyseg - 1;
}

double
LZWBinFa::getAtlag (void)
{
  melyseg = atlagosszeg = atlagdb = 0;
  ratlag (&gyoker);
  atlag = ((double) atlagosszeg) / atlagdb;
  return atlag;
}

double
LZWBinFa::getSzoras (void)
{
  atlag = getAtlag ();
  szorasosszeg = 0.0;
  melyseg = atlagdb = 0;

  rszoras (&gyoker);

  if (atlagdb - 1 > 0)
    szoras = std::sqrt (szorasosszeg / (atlagdb - 1));
  else
    szoras = std::sqrt (szorasosszeg);

  return szoras;
}

void
LZWBinFa::rmelyseg (Csomopont * elem)
{
  if (elem != NULL)
    {
      ++melyseg;
      if (melyseg > maxMelyseg)
	maxMelyseg = melyseg;
      rmelyseg (elem->egyesGyermek ());
      // ez a postorder bejÃ¡rÃ¡shoz kÃ©pest
      // 1-el nagyobb mÃ©lysÃ©g, ezÃ©rt -1
      rmelyseg (elem->nullasGyermek ());
      --melyseg;
    }
}

void
LZWBinFa::ratlag (Csomopont * elem)
{
  if (elem != NULL)
    {
      ++melyseg;
      ratlag (elem->egyesGyermek ());
      ratlag (elem->nullasGyermek ());
      --melyseg;
      if (elem->egyesGyermek () == NULL && elem->nullasGyermek () == NULL)
	{
	  ++atlagdb;
	  atlagosszeg += melyseg;
	}
    }
}

void
LZWBinFa::rszoras (Csomopont * elem)
{
  if (elem != NULL)
    {
      ++melyseg;
      rszoras (elem->egyesGyermek ());
      rszoras (elem->nullasGyermek ());
      --melyseg;
      if (elem->egyesGyermek () == NULL && elem->nullasGyermek () == NULL)
	{
	  ++atlagdb;
	  szorasosszeg += ((melyseg - atlag) * (melyseg - atlag));
	}
    }
}
*/
    public int getMelyseg() {
        melyseg = maxMelyseg = 0;
        rmelyseg(gyoker);
        return maxMelyseg - 1;
    }

    public double getAtlag() {
        melyseg = atlagosszeg = atlagdb = 0;
        ratlag(gyoker);
        atlag = ((double) atlagosszeg) / atlagdb;
        return atlag;
    }

    public double getSzoras() {
        atlag = getAtlag();
        szorasosszeg = 0.0;
        melyseg = atlagdb = 0;

        rszoras(gyoker);

        if (atlagdb - 1 > 0) {
            szoras = Math.sqrt(szorasosszeg / (atlagdb - 1));
        } else {
            szoras = Math.sqrt(szorasosszeg);
        }

        return szoras;
    }

    public void rmelyseg(Csomopont elem) {
        if (elem != null) {
            ++melyseg;
            if (melyseg > maxMelyseg) {
                maxMelyseg = melyseg;
            }
            rmelyseg(elem.egyesGyermek());
            // ez a postorder bejÃ¡rÃ¡shoz kÃ©pest
            // 1-el nagyobb mÃ©lysÃ©g, ezÃ©rt -1
            rmelyseg(elem.nullasGyermek());
            --melyseg;
        }
    }

    public void ratlag(Csomopont elem) {
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

    public void rszoras(Csomopont elem) {
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

// teszt pl.: http://progpater.blog.hu/2011/03/05/labormeres_otthon_avagy_hogyan_dolgozok_fel_egy_pedat
// [norbi@sgu ~]$ echo "01111001001001000111"|./z3a2
// ------------1(3)
// ---------1(2)
// ------1(1)
// ---------0(2)
// ------------0(3)
// ---------------0(4)
// ---/(0)
// ---------1(2)
// ------0(1)
// ---------0(2)
// depth = 4
// mean = 2.75
// var = 0.957427
// a laborvÃ©dÃ©shez majd ezt a tesztelÃ©st hasznÃ¡ljuk:
// http://

  /* Ez volt eddig a main, de most komplexebb kell, mert explicite bejÃ¶vÅ‘, kimenÅ‘ fÃ¡jlokkal kell dolgozni
int
main ()
{
    char b;
    LZWBinFa binFa;

    while (std::cin >> b)
    {
        binFa << b;
    }

    //std::cout << binFa.kiir (); // Ã­gy rajzolt ki a fÃ¡t a korÃ¡bbi verziÃ³kban de, hogy izgalmasabb legyen
    // a pÃ©lda, azaz ki lehessen tolni az LZWBinFa-t kimeneti csatornÃ¡ra:

    std::cout << binFa; // ehhez kell a globÃ¡lis operator<< tÃºlterhelÃ©se, lÃ¡sd fentebb

    std::cout << "depth = " << binFa.getMelyseg () << std::endl;
    std::cout << "mean = " << binFa.getAtlag () << std::endl;
    std::cout << "var = " << binFa.getSzoras () << std::endl;

    binFa.szabadit ();

    return 0;
}
*/

    /* A parancssor arg. kezelÃ©st egyszerÅ±en bedolgozzuk a 2. hullÃ¡m kapcsolÃ³dÃ³ feladatÃ¡bÃ³l:
   http://progpater.blog.hu/2011/03/12/hey_mikey_he_likes_it_ready_for_more_3
   de mivel nekÃ¼nk sokkal egyszerÅ±bb is elÃ©g, alig hagyunk meg belÅ‘le valamit...
   */
  /*
void
usage (void)
{
  std::cout << "Usage: lzwtree in_file -o out_file" << std::endl;
}
*/
    public static void usage() {
        System.out.println("Usage: lzwtree in_file -o out_file");
    }

    /*
  int
  main (int argc, char *argv[])
  {
  */
}
