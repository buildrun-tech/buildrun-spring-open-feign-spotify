package tech.buildrun.spotify.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.buildrun.spotify.client.Album;
import tech.buildrun.spotify.client.AlbumSpotifyClient;
import tech.buildrun.spotify.client.AuthSpotifyClient;
import tech.buildrun.spotify.client.LoginRequest;

import java.util.List;

@RestController
@RequestMapping("/spotify/api")
public class AlbumController {

    private final AuthSpotifyClient authSpotifyClient;
    private final AlbumSpotifyClient albumSpotifyClient;

    public AlbumController(AuthSpotifyClient authSpotifyClient,
                           AlbumSpotifyClient albumSpotifyClient) {
        this.authSpotifyClient = authSpotifyClient;
        this.albumSpotifyClient = albumSpotifyClient;
    }

    @GetMapping("/albums")
    public ResponseEntity<List<Album>> helloWorld() {

        var request = new LoginRequest(
                  "client_credentials",
                    "seu_client_id",
                 "seu_client_secret"
        );
        var token = authSpotifyClient.login(request).getAccessToken();

        var response = albumSpotifyClient.getReleases("Bearer " + token);


        return ResponseEntity.ok(response.getAlbums().getItems());
    }
}
