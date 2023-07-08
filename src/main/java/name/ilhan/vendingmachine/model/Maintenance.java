package name.ilhan.vendingmachine.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Maintenance {
    /**
     * True for maintenance mode on, false for maintenance mode off.
     */
    public Boolean maintenanceMode;
}
