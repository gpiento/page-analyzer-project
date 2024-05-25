package hexlet.code.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class MainPage {

    private Boolean visited;
    private String currentUser;

    public Boolean isVisited() {
            return visited;
    }
}
