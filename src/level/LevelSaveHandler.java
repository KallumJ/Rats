package level;

import io.XMLFileWriter;
import io.XMLNode;
import players.scores.Player;

import java.io.File;
import java.util.UUID;

public class LevelSaveHandler {
    public static void saveLevel(LevelData levelData, Player player) {
        UUID uuid = UUID.randomUUID();
        File levelFile = new File("levels/savedLevels/" + uuid + ".xml");

        XMLFileWriter xmlFileWriter = new XMLFileWriter(levelFile, "level");

        saveLevelProperties(xmlFileWriter, levelData, player);
    }

    private static void saveLevelProperties(XMLFileWriter xmlFileWriter, LevelData levelData, Player player) {
        LevelProperties levelProperties = levelData.getLevelProperties();

        String levelHeight = String.valueOf(levelProperties.getLevelHeight());
        String levelWidth = String.valueOf(levelProperties.getLevelWidth());
        String populationToLose = String.valueOf(levelProperties.getPopulationToLose());
        String expectedTime = String.valueOf(levelProperties.getExpectedTime());
        String id = String.valueOf(levelProperties.getLevelId());
        String itemInterval = String.valueOf(levelProperties.getItemInterval());

        XMLNode levelHeightNode = new XMLNode("levelHeight", levelHeight, null, null);

    }
}
