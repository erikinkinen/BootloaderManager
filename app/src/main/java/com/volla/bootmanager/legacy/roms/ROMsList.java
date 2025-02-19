package com.volla.bootmanager.legacy.roms;

import android.annotation.SuppressLint;
import android.content.Context;

import com.topjohnwu.superuser.io.SuFile;

import com.volla.bootmanager.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class ROMsList {
    public final String codename;
    public final Context c;

    public ROMsList(String codename, Context context) {
        this.codename = codename;
        c = context;
    }

    @SuppressLint("SdCardPath")
    public List<ROM> getROMs() {
        ArrayList<ROM> l = new ArrayList<>();
        for (String sn : Objects.requireNonNull(SuFile.open("/data/data/com.volla.bootmanager/assets/Scripts/add_os/" + codename + "/").list())) {
            ROM r = new ROM();
            r.scriptname = sn;
            r.fullPath = "/data/data/com.volla.bootmanager/assets/Scripts/add_os/" + codename + "/" + r.scriptname;
            r.requiredFiles = new HashMap<>();
            r.parts = new ArrayList<>();
            r.strings = new HashMap<>();
            r.flashes = new HashMap<>();
            r.updates = new ArrayList<>();
            ArrayList<String> a = new ArrayList<>(Arrays.asList(Objects.requireNonNull(SuFile.open("/data/abm/bootset/db/entries/").list())));
            a.removeIf((c) -> !c.contains("rom"));
            a.sort((c, d) -> Integer.compare(Integer.parseInt(c.replace("rom","").replace(".conf","")), Integer.parseInt(d.replace("rom","").replace(".conf",""))));
            int b = a.size() > 0 ? Integer.parseInt(a.get(a.size()-1).replace("rom","").replace(".conf",""))+1 : 0;
            switch (r.scriptname) {
                case "add_ubuntutouch_systemimage_haliumboot.sh":
                    r.type = ROMType.UBUNTU;
                    r.viewname = c.getString(R.string.rom_type_add_ut_sysimg_halium);
                    r.requiredFiles.put("halium-boot.img", c.getString(R.string.select_halium_boot));
                    r.flashes.put("ubuntu.img", new String[] {c.getString(R.string.select_system_image), c.getString(R.string.select_part, c.getString(R.string.system_part))});
                    r.parts.add(c.getString(R.string.select_part, c.getString(R.string.data_part)));
                    r.strings.put(c.getString(R.string.enter_rom_name), "Ubuntu Touch");
                    r.strings.put(c.getString(R.string.enter_rom_folder), "rom" + b);
                    r.gen = (imodel, menuName, folderName) -> imodel.setCmdline(Objects.requireNonNull(imodel.getROM().getValue()).fullPath + " '" + menuName + "' '" + folderName + "' " + Objects.requireNonNull(imodel.getParts().getValue()).get(0) + " " + imodel.getParts().getValue().get(1) + " /data/data/com.volla.bootmanager/cache/halium-boot.img");
                    break;
                case "add_droidian.sh":
                    r.type = ROMType.DROIDIAN;
                    r.viewname = c.getString(R.string.rom_type_add_droidian);
                    r.requiredFiles.put("droidian.zip", c.getString(R.string.select_droidian));
                    r.parts.add(c.getString(R.string.select_part, c.getString(R.string.data_part)));
                    r.strings.put(c.getString(R.string.enter_rom_name), "Droidian (phosh)");
                    r.strings.put(c.getString(R.string.enter_rom_folder), "rom" + b);
                    r.gen = (imodel, menuName, folderName) -> imodel.setCmdline(Objects.requireNonNull(imodel.getROM().getValue()).fullPath + " '" + menuName + "' '" + folderName + "' " + Objects.requireNonNull(imodel.getParts().getValue()).get(0) + " /data/data/com.volla.bootmanager/cache/droidian.zip");
                    break;
                case "add_droidian_cutie.sh":
                    r.type = ROMType.DROIDIAN_CUTIE;
                    r.viewname = c.getString(R.string.rom_type_add_droidian_cutie);
                    r.requiredFiles.put("droidian.zip", c.getString(R.string.select_droidian));
                    r.parts.add(c.getString(R.string.select_part, c.getString(R.string.data_part)));
                    r.strings.put(c.getString(R.string.enter_rom_name), "Droidian (Cutie Shell)");
                    r.strings.put(c.getString(R.string.enter_rom_folder), "rom" + b);
                    r.gen = (imodel, menuName, folderName) -> imodel.setCmdline(Objects.requireNonNull(imodel.getROM().getValue()).fullPath + " '" + menuName + "' '" + folderName + "' " + Objects.requireNonNull(imodel.getParts().getValue()).get(0) + " /data/data/com.volla.bootmanager/cache/droidian.zip");
                    break;
                case "add_sailfish.sh":
                    r.type = ROMType.SAILFISH;
                    r.viewname = c.getString(R.string.rom_type_add_sailfish);
                    r.requiredFiles.put("halium-boot.img", c.getString(R.string.select_halium_boot));
                    r.flashes.put("system.img", new String[] {c.getString(R.string.select_system_image), c.getString(R.string.select_part, c.getString(R.string.data_part))});
                    r.parts.add(c.getString(R.string.select_part, c.getString(R.string.data_part)));
                    r.strings.put(c.getString(R.string.enter_rom_name), "SailfishOS");
                    r.strings.put(c.getString(R.string.enter_rom_folder), "rom" + b);
                    r.gen = (imodel, menuName, folderName) -> imodel.setCmdline(Objects.requireNonNull(imodel.getROM().getValue()).fullPath + " '" + menuName + "' '" + folderName + "' " + Objects.requireNonNull(imodel.getParts().getValue()).get(0) + " " + imodel.getParts().getValue().get(1) + " /data/data/com.volla.bootmanager/cache/halium-boot.img");
                    break;
                case "other_os.sh":
                    r.type = ROMType.OTHER;
                    r.viewname = c.getString(R.string.other_os);
                    r.requiredFiles.put("boot.img", c.getString(R.string.select_boot));
                    r.strings.put(c.getString(R.string.enter_rom_name), "");
                    r.strings.put(c.getString(R.string.enter_rom_folder), "rom" + b);
                    r.gen = (imodel, menuName, folderName) -> imodel.setCmdline(Objects.requireNonNull(imodel.getROM().getValue()).fullPath + " '" + folderName + "' '" + menuName + "' /data/data/com.volla.bootmanager/cache/boot.img");
                    break;
                case "entry_only.sh":
                    r.type = ROMType.ENTRY;
                    r.viewname = c.getString(R.string.empty_entry);
                    r.strings.put(c.getString(R.string.enter_rom_name), "");
                    r.strings.put(c.getString(R.string.enter_rom_folder), "rom" + b);
                    r.gen = (imodel, menuName, folderName) -> imodel.setCmdline(Objects.requireNonNull(imodel.getROM().getValue()).fullPath + " '" + folderName + "' '" + menuName + "'");
                    break;
                default:
                    r = null;
                    break;
            }
            if (r != null)
                l.add(r);
        }
        return l;
    }
}
