package main.command;

import main.command.artistPlaylist.ArtistPlaylistWorkflow;
import main.command.GetLikeArtistInfo.LikedArtistInfoWorkflow;
import main.command.language.LanguagePlaylistWorkflow;

// creates the right workflow based on a feature name
public class WorkflowFactory implements IWorkflowFactory {

    public ICommandWorkflow create(String feature) {
        return switch (feature) {
            case "artist-playlist" -> new ArtistPlaylistWorkflow();
            case "liked-artist-info" -> new LikedArtistInfoWorkflow();
            case "language-playlist" -> new LanguagePlaylistWorkflow();
            default -> throw new IllegalArgumentException("Unknown feature: " + feature);
        };
    }
}
