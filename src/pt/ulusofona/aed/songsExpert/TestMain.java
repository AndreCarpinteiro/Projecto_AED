package pt.ulusofona.aed.songsExpert;

import org.junit.Test;
import static org.junit.Assert.*;

class TestMain {

    @Test
    public void contaNumeroMusicas(){
        Main.lerFicheiros("test-files/deisi_artists.csv","test-files/deisi_songs.csv","test-files/deisi_artist_location.csv","test-files/deisi_artist_similarity.csv");
        String query = "COUNT_SONGS";
        String respostaReal = Main.askTheExpert(query);
        String respostaEsperada = "8";
        assertEquals("Devia ser 8",respostaEsperada,respostaReal);
    }

    @Test
    public void contarMusicasSemAno(){
        Main.lerFicheiros("test-files/deisi_artists.csv","test-files/deisi_songs.csv","test-files/deisi_artist_location.csv","test-files/deisi_artist_similarity.csv");
        String query = "COUNT_SONGS_WITHOUT_YEAR";
        String respostaReal = Main.askTheExpert(query);
        String respostaEsperada = "1";
        assertEquals("Devia ser 1",respostaEsperada,respostaReal);
    }

    @Test
    public void contaArtistasComMaisDeXMusicas(){
        Main.lerFicheiros("test-files/deisi_artists.csv","test-files/deisi_songs.csv","test-files/deisi_artist_location.csv","test-files/deisi_artist_similarity.csv");
        String query = "COUNT_ARTISTS_MANY_SONGS 1";
        String respostaReal = Main.askTheExpert(query);
        String respostaEsperada = "3";
        assertEquals("Devia ser 3",respostaEsperada,respostaReal);
    }

    @Test
    public void contaMusicasComMaisDeXArtistas(){
        Main.lerFicheiros("test-files/deisi_artists.csv","test-files/deisi_songs.csv","test-files/deisi_artist_location.csv","test-files/deisi_artist_similarity.csv");
        String query = "COUNT_SONGS_MANY_ARTISTS 1";
        String respostaReal = Main.askTheExpert(query);
        String respostaEsperada = "2";
        assertEquals("Devia ser 2",respostaEsperada,respostaReal);
    }

    @Test
    public void contaMusicasUnicas(){//FIXME
        Main.lerFicheiros("test-files/deisi_artists.csv","test-files/deisi_songs.csv","test-files/deisi_artist_location.csv","test-files/deisi_artist_similarity.csv");
        String query = "COUNT_UNIQUE_TITLES";
        String respostaReal = Main.askTheExpert(query);
        String respostaEsperada = "8";
        assertEquals("Devia ser 8",respostaEsperada,respostaReal);
    }

    @Test
    public void chamarMusicasComArtistaX(){
        Main.lerFicheiros("test-files/deisi_artists.csv","test-files/deisi_songs.csv","test-files/deisi_artist_location.csv","test-files/deisi_artist_similarity.csv");
        String query = "GET_SONGS_WITH_ARTISTS";
        String respostaReal = Main.askTheExpert(query);
        String respostaEsperada = "8";
        assertEquals("Devia ser 8",respostaEsperada,respostaReal);
    }

    @Test
    public void chamaTopXArtistasDeAnoEspecifico(){
        Main.lerFicheiros("test-files/deisi_artists.csv","test-files/deisi_songs.csv","test-files/deisi_artist_location.csv","test-files/deisi_artist_similarity.csv");
        String query = "GET_TOP_ARTISTS_WITH_SONGS_IN_YEAR";
        String respostaReal = Main.askTheExpert(query);
        String respostaEsperada = "8";
        assertEquals("Devia ser 8",respostaEsperada,respostaReal);
    }

    @Test
    public void chamaMusicasUnicasParaArtistaX(){
        Main.lerFicheiros("test-files/deisi_artists.csv","test-files/deisi_songs.csv","test-files/deisi_artist_location.csv","test-files/deisi_artist_similarity.csv");
        String query = "GET_UNIQUE_SONGS_BY_ARTIST";
        String respostaReal = Main.askTheExpert(query);
        String respostaEsperada = "8";
        assertEquals("Devia ser 8",respostaEsperada,respostaReal);
    }

    @Test
    public void contaArtistasComPalavraXEmMusicas(){
        Main.lerFicheiros("test-files/deisi_artists.csv","test-files/deisi_songs.csv","test-files/deisi_artist_location.csv","test-files/deisi_artist_similarity.csv");
        String query = "COUNT_ARTISTS_WITH_WORD_IN_SONG_TITLE";
        String respostaReal = Main.askTheExpert(query);
        String respostaEsperada = "8";
        assertEquals("Devia ser 8",respostaEsperada,respostaReal);
    }

    @Test
    public void chamaOsArtistasComLocalizacaoX(){
        Main.lerFicheiros("test-files/deisi_artists.csv","test-files/deisi_songs.csv","test-files/deisi_artist_location.csv","test-files/deisi_artist_similarity.csv");
        String query = "GET_ARTISTS_WITH_LOCATION";
        String respostaReal = Main.askTheExpert(query);
        String respostaEsperada = "8";
        assertEquals("Devia ser 8",respostaEsperada,respostaReal);
    }

    @Test
    public void chamaTopXArtistasComMaisTemasDeLocalizacaoY(){
        Main.lerFicheiros("test-files/deisi_artists.csv","test-files/deisi_songs.csv","test-files/deisi_artist_location.csv","test-files/deisi_artist_similarity.csv");
        String query = "TOP_N_ARTISTS_WITH_LOCATION";
        String respostaReal = Main.askTheExpert(query);
        String respostaEsperada = "8";
        assertEquals("Devia ser 8",respostaEsperada,respostaReal);
    }

    @Test
    public void chamaPalavraComMaisOcorrenciaComTamanhoX(){
        Main.lerFicheiros("test-files/deisi_artists.csv","test-files/deisi_songs.csv","test-files/deisi_artist_location.csv","test-files/deisi_artist_similarity.csv");
        String query = "MOST_FREQUENT_WORD_TITLES";
        String respostaReal = Main.askTheExpert(query);
        String respostaEsperada = "8";
        assertEquals("Devia ser 8",respostaEsperada,respostaReal);
    }

    @Test
    public void chamaArtistaComMaisMusicasDeAnoX(){
        Main.lerFicheiros("test-files/deisi_artists.csv","test-files/deisi_songs.csv","test-files/deisi_artist_location.csv","test-files/deisi_artist_similarity.csv");
        String query = "ARTIST_MOST_UNIQUE_SONGS";
        String respostaReal = Main.askTheExpert(query);
        String respostaEsperada = "8";
        assertEquals("Devia ser 8",respostaEsperada,respostaReal);
    }

    @Test
    public void chamaTopXArtistasComMaisArtistasSemelhantes(){
        Main.lerFicheiros("test-files/deisi_artists.csv","test-files/deisi_songs.csv","test-files/deisi_artist_location.csv","test-files/deisi_artist_similarity.csv");
        String query = "TOP_N_SIMILAR_ARTISTS";
        String respostaReal = Main.askTheExpert(query);
        String respostaEsperada = "8";
        assertEquals("Devia ser 8",respostaEsperada,respostaReal);
    }

    @Test
    public void indicaOsArtistasSemelhantesDeAmbosOsArtistas(){
        Main.lerFicheiros("test-files/deisi_artists.csv","test-files/deisi_songs.csv","test-files/deisi_artist_location.csv","test-files/deisi_artist_similarity.csv");
        String query = "WHY_SO_SIMILAR?";
        String respostaReal = Main.askTheExpert(query);
        String respostaEsperada = "8";
        assertEquals("Devia ser 8",respostaEsperada,respostaReal);
    }

    @Test
    public void contaOsTemasEmQueOArtistasParticipaComRestricoes(){
        Main.lerFicheiros("test-files/deisi_artists.csv","test-files/deisi_songs.csv","test-files/deisi_artist_location.csv","test-files/deisi_artist_similarity.csv");
        String query = "COUNT_SONGS_BY_ARTIST_WITH_RESTRICTIONS";
        String respostaReal = Main.askTheExpert(query);
        String respostaEsperada = "8";
        assertEquals("Devia ser 8",respostaEsperada,respostaReal);
    }

    @Test
    public void insereMusica(){
        Main.lerFicheiros("test-files/deisi_artists.csv","test-files/deisi_songs.csv","test-files/deisi_artist_location.csv","test-files/deisi_artist_similarity.csv");
        String query = "INSERT_SONG";
        String respostaReal = Main.askTheExpert(query);
        String respostaEsperada = "8";
        assertEquals("Devia ser 8",respostaEsperada,respostaReal);
    }

    @Test
    public void removeMusica(){
        Main.lerFicheiros("test-files/deisi_artists.csv","test-files/deisi_songs.csv","test-files/deisi_artist_location.csv","test-files/deisi_artist_similarity.csv");
        String query = "REMOVE_SONG";
        String respostaReal = Main.askTheExpert(query);
        String respostaEsperada = "8";
        assertEquals("Devia ser 8",respostaEsperada,respostaReal);
    }
}