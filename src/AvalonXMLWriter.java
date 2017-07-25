import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.FileOutputStream;

/**
 * Created by Matt on 7/25/2017.
 */
public class AvalonXMLWriter{

    static void writeGameStats(){
        try{
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document gameDataFile = documentBuilder.newDocument();

            Element root = gameDataFile.createElement("avalon");
            gameDataFile.appendChild(root);

            Element totalGamesElement = gameDataFile.createElement("totalGames");
            totalGamesElement.appendChild(
                    gameDataFile.createTextNode(Double.toString(avalon.totalGames))
            );
            root.appendChild(totalGamesElement);

            Element totalGoodWinsElement = gameDataFile.createElement("totalGoodWins");
            totalGoodWinsElement.appendChild(
                    gameDataFile.createTextNode(Double.toString(avalon.totalGoodWins))
            );
            root.appendChild(totalGoodWinsElement);

            Element totalBadWinsElement = gameDataFile.createElement("totalBadWins");
            totalBadWinsElement.appendChild(
                    gameDataFile.createTextNode(Double.toString(avalon.totalBadWins))
            );
            root.appendChild(totalBadWinsElement);

            Element totalAssassinationsElement = gameDataFile.createElement("totalAssassinations");
            totalAssassinationsElement.appendChild(
                    gameDataFile.createTextNode(Double.toString(avalon.totalAssassinations))
            );
            root.appendChild(totalAssassinationsElement);

            try{
                Transformer tr = TransformerFactory.newInstance().newTransformer();
                tr.setOutputProperty(OutputKeys.INDENT, "yes");
                tr.setOutputProperty(OutputKeys.METHOD, "xml");
                tr.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
                tr.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

                tr.transform(new DOMSource(gameDataFile), new StreamResult(new FileOutputStream("./data/gamedata.avalon")));
            } catch (TransformerException te) {
                System.out.println(te.getMessage());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void writePlayerStats(){
        try{
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();

            for(Player player:avalon.playerList.values()){
                Document playerFile = documentBuilder.newDocument();
                Element root = playerFile.createElement("player");
                playerFile.appendChild(root);

                Element playerName = playerFile.createElement("playerName");
                playerName.appendChild(
                        playerFile.createTextNode(player.getPlayerName())
                );
                root.appendChild(playerName);

                Element gamesPlayed = playerFile.createElement("gamesPlayed");
                gamesPlayed.appendChild(
                        playerFile.createTextNode(Double.toString(player.getGamesTotal()))
                );
                root.appendChild(gamesPlayed);

                Element gamesWon = playerFile.createElement("gamesWon");
                gamesWon.appendChild(
                        playerFile.createTextNode(Double.toString(player.getGamesWon()))
                );
                root.appendChild(gamesWon);

                Element gamesWonGood = playerFile.createElement("gamesWonGood");
                gamesWonGood.appendChild(
                        playerFile.createTextNode(Double.toString(player.getGamesWonGood()))
                );
                root.appendChild(gamesWonGood);

                Element gamesWonBad = playerFile.createElement("gamesWonBad");
                gamesWonBad.appendChild(
                        playerFile.createTextNode(Double.toString(player.getGamesWonBad()))
                );
                root.appendChild(gamesWonBad);

                Element timesGood = playerFile.createElement("timesGood");
                timesGood.appendChild(
                        playerFile.createTextNode(Double.toString(player.getTimesGood()))
                );
                root.appendChild(timesGood);

                Element timesBad = playerFile.createElement("timesBad");
                timesBad.appendChild(
                        playerFile.createTextNode(Double.toString(player.getTimesBad()))
                );
                root.appendChild(timesBad);

                for(Role role:player.getRolesTable().values()){
                    Element roleElement = playerFile.createElement("role");

                    Element roleName = playerFile.createElement("roleName");
                    roleName.appendChild(
                            playerFile.createTextNode(role.getXMLName())
                    );
                    roleElement.appendChild(roleName);

                    Element gamesPlayedinRole = playerFile.createElement("gamesPlayed");
                    gamesPlayedinRole.appendChild(
                            playerFile.createTextNode(Double.toString(role.getGamesPlayed()))
                    );
                    roleElement.appendChild(gamesPlayedinRole);

                    Element gamesWoninRole = playerFile.createElement("gamesWon");
                    gamesWoninRole.appendChild(
                            playerFile.createTextNode(Double.toString(role.getGamesWon()))
                    );
                    roleElement.appendChild(gamesWoninRole);

                    root.appendChild(roleElement);
                }
                try {
                    Transformer tr = TransformerFactory.newInstance().newTransformer();
                    tr.setOutputProperty(OutputKeys.INDENT, "yes");
                    tr.setOutputProperty(OutputKeys.METHOD, "xml");
                    tr.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
                    tr.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

                    tr.transform(new DOMSource(playerFile),
                            new StreamResult(new FileOutputStream("data/players/"+player.getPlayerName()+".player")));
                } catch (TransformerException te) {
                    System.out.println(te.getMessage());
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
