/**
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 3
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 *
 */

package tntrun.commands.setup.arena;

import org.bukkit.entity.Player;

import tntrun.TNTRun;
import tntrun.arena.Arena;
import tntrun.commands.setup.CommandHandlerInterface;
import tntrun.selectionget.PlayerCuboidSelection;
import tntrun.selectionget.PlayerSelection;

public class SetGameLevel implements CommandHandlerInterface {

	private TNTRun plugin;
	private PlayerSelection selection;
	public SetGameLevel(TNTRun plugin, PlayerSelection selection) {
		this.plugin = plugin;
		this.selection = selection;
	}

	@Override
	public boolean handleCommand(Player player, String[] args) {
		Arena arena = plugin.amanager.getArenaByName(args[0]);
		if (arena != null) {
			if (arena.getStatusManager().isArenaEnabled()) {
				player.sendMessage("Disable arena first");
				return true;
			}
			PlayerCuboidSelection sel = selection.getPlayerSelection(player, true);
			if (sel != null) {
				if (arena.getStructureManager().setGameLevel(args[1], sel.getMinimumLocation(), sel.getMaximumLocation())) {
					player.sendMessage("GameLevel set");
				} else {
					player.sendMessage("GameLevel should be in arena bounds");
				}
			} else {
				player.sendMessage("Locations are wrong or not defined");
			}
		} else {
			player.sendMessage("Arena does not exist");
		}
		return true;
	}

}