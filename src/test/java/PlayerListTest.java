import junit.framework.*;

import java.util.ArrayList;
import java.util.UUID;

import org.redcraft.redcraftbungeejsonapi.PlayerInfo;
import org.redcraft.redcraftbungeejsonapi.PlayerList;

public class PlayerListTest extends TestCase {
	protected PlayerInfo fakePlayerInfo;
	protected PlayerList fakePlayerList;

	protected void setUp() {
		fakePlayerInfo = new PlayerInfo(UUID.fromString("ffffffff-ffff-ffff-ffff-ffffffffffff"), "fake_player", "§4fake_player");

		ArrayList<PlayerInfo> fakePlayerInfoList = new ArrayList<PlayerInfo>();
		fakePlayerInfoList.add(fakePlayerInfo);

		fakePlayerList = new PlayerList();
		fakePlayerList.players.put("fake_server", fakePlayerInfoList);
	}

   public void testSerialization() {
	   	String expectedSerializedPlayerList = "{\"players\":{\"fake_server\":[{\"uuid\":\"ffffffff-ffff-ffff-ffff-ffffffffffff\",\"name\":\"fake_player\",\"displayName\":\"§4fake_player\"}]}}";
		String actualSerializedPlayers = fakePlayerList.getOnlinePlayersJson();
		assertEquals(expectedSerializedPlayerList, actualSerializedPlayers);
   }
}