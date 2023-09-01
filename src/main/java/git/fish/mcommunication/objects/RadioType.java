package git.fish.mcommunication.objects;


import git.fish.mcommunication.settings.Settings;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Getter
public enum RadioType {
        WEAKRADIO(Settings.IMP.WRADIO, 200, 40, Settings.IMP.WMATERIAL),
        MEDIUMRADIO(Settings.IMP.MRADIO, 400, 60, Settings.IMP.MMATERIAL),
        STRONGRADIO(Settings.IMP.SRADIO, 600, 80, Settings.IMP.SMATERIAL);


        String type;
        int distance, lucky;
        public ItemStack Item;

        RadioType(String type, int distance, int lucky, String item) {
                this.type = type;
                this.distance = distance;
                this.lucky = lucky;
                this.Item = new ItemStack(Material.valueOf(item));
        }
    }
