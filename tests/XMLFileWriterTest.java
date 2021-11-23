import io.XMLFileWriter;
import io.XMLNode;
import org.junit.Test;
import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class XMLFileWriterTest {

    @Test
    public void testLevelWriter() throws FileNotFoundException {
        String expectedOuput = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><level><levelProperties><id>1</id><height>4</height><tile sex_ch=\"f\">g</tile></levelProperties></level>";

        XMLNode levelId = new XMLNode("id", "1", null, null);
        XMLNode levelHeight = new XMLNode("height", "4", null, null);
        HashMap<String, String> sexChange = new HashMap<>();
        sexChange.put("sex_ch", "f");

        XMLNode tile = new XMLNode("tile", "g", sexChange,null);

        ArrayList<XMLNode> levelPropertiesChildren = new ArrayList<>();
        levelPropertiesChildren.add(levelId);
        levelPropertiesChildren.add(levelHeight);
        levelPropertiesChildren.add(tile);

        XMLNode levelProperties = new XMLNode("levelProperties", null, null, levelPropertiesChildren);

        File testFile = new File("test.xml");
        XMLFileWriter fileWriter = new XMLFileWriter(testFile, "level");
        fileWriter.writeNode(levelProperties);
        fileWriter.saveAndClose();

        Scanner scanner = new Scanner(testFile);
        String actualOutput = scanner.nextLine();
        scanner.close();
        testFile.delete();

        assertEquals(expectedOuput, actualOutput);

    }

}
