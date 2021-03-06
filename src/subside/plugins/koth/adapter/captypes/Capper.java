package subside.plugins.koth.adapter.captypes;

import java.util.List;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.json.simple.JSONObject;

import subside.plugins.koth.adapter.Capable;
import subside.plugins.koth.adapter.Koth;
import subside.plugins.koth.adapter.KothHandler;

public abstract class Capper {
    
    public abstract String getUniqueClassIdentifier();
    public abstract String getUniqueObjectIdentifier();
    public abstract boolean isInOrEqualTo(OfflinePlayer oPlayer);
    public abstract String getName();
    public abstract Object getObject();
    public abstract boolean areaCheck(Capable cap);
    public abstract List<Player> getAvailablePlayers(Koth koth);
    
    
    @Deprecated
    public static Capper load(JSONObject obj){
        return KothHandler.getInstance().getCapEntityRegistry().getCapperFromType((String)obj.get("capperType"), (String)obj.get("uniqueId"));
    }

    @Deprecated
    @SuppressWarnings("unchecked")
    public JSONObject save(){
        JSONObject obj = new JSONObject();
        obj.put("capperType", getUniqueClassIdentifier()); // Class identifier
        obj.put("uniqueId", getUniqueObjectIdentifier()); // Object identifier
        return obj;
    }
}
