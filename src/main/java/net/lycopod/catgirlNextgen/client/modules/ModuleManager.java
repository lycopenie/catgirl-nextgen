package net.lycopod.catgirlNextgen.client.modules;

import net.lycopod.catgirlNextgen.client.modules.combat.CartPlacer;
import net.lycopod.catgirlNextgen.client.modules.combat.TriggerBot;
import net.lycopod.catgirlNextgen.client.modules.player.AutoTotem;

import java.util.ArrayList;
import java.util.List;


public class ModuleManager {
    public static final ModuleManager INSTANCE = new ModuleManager();

    private final List<Module> modules = new ArrayList<>();

    public ModuleManager() {
        modules.add(new TriggerBot());
        modules.add(new CartPlacer());
        modules.add(new AutoTotem());
    }


    public void onTick() {
        for (Module m : getEnabledModules()) {
            m.onTick();
        }
    }

    public List<Module> getEnabledModules() {
        List<Module> enabled = new ArrayList<>();
        for (Module module: modules) {
            if (module.isEnabled()) enabled.add(module);
        }

        return enabled;
    }

    public List<Module> getAllModules() {
        return modules;
    }

    public List<Module> getModulesInCategory(Module.Category category) {
        List<Module> needed = new ArrayList<>();
        for (Module m : modules) {
            if (m.getCategory() == category) {
                needed.add(m);
            }
        }

        return needed;
    }
}