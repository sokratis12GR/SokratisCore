package sokratis12gr.sokratiscore;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import sokratis12gr.sokratiscore.handlers.FileHandler;
import sokratis12gr.sokratiscore.network.PacketSyncableObject;
import sokratis12gr.sokratiscore.network.PacketTileMessage;
import sokratis12gr.sokratiscore.proxy.CommonProxy;
import sokratis12gr.sokratiscore.util.LogHelper;


@Mod(modid = SokratisCore.MODID, name = SokratisCore.MODNAME, version = SokratisCore.VERSION, updateJSON = "https://raw.githubusercontent.com/sokratis12GR/VersionUpdate/gh-pages/SokratisCore.json")
public class SokratisCore {
    public static final String MODNAME = "Sokratis Core";
    public static final String MODID = "sokratiscore";
    public static final String VERSION = "1.10.2-1.1.0";

    @Mod.Instance(SokratisCore.MODID)
    public static SokratisCore instance;

    @SidedProxy(clientSide = "sokratis12gr.sokratiscore.proxy.ClientProxy", serverSide = "sokratis12gr.sokratiscore.CommonProxy")
    public static CommonProxy proxy;

    public static SimpleNetworkWrapper network;

    public SokratisCore() {
        LogHelper.info("Welcoming Minecraft");
    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        proxy.preInit(event);

        FileHandler.init(event);
        registerNetwork();
    }

    public void registerNetwork() {
        network = NetworkRegistry.INSTANCE.newSimpleChannel("SokCoreNet");
        network.registerMessage(PacketSyncableObject.Handler.class, PacketSyncableObject.class, 0, Side.CLIENT);
        network.registerMessage(PacketTileMessage.Handler.class, PacketTileMessage.class, 1, Side.SERVER);
    }
}