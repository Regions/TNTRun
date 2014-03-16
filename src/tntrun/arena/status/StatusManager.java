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

package tntrun.arena.status;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import tntrun.arena.Arena;
import tntrun.arena.structure.GameLevel;
import tntrun.messages.Messages;

public class StatusManager {

	private Arena arena;
	public StatusManager(Arena arena) {
		this.arena = arena;
	}

	private boolean enabled = false;
	private boolean starting = false;
	private boolean running = false;
	private boolean regenerating = false;

	public boolean isArenaEnabled() {
		return enabled;
	}

	public boolean enableArena() {
		if (arena.getStructureManager().isArenaConfigured().equalsIgnoreCase("yes")) {
			enabled = true;
			arena.getGameHandler().startArenaAntiLeaveHandler();
			arena.plugin.signEditor.modifySigns(arena.getArenaName());
			return true;
		}
		return false;
	}

	public void disableArena() {
		enabled = false;
		// drop players
		for (Player player : Bukkit.getOnlinePlayers()) {
			if (arena.getPlayersManager().isPlayerInArena(player.getName())) {
				arena.getPlayerHandler().leavePlayer(player, Messages.arenadisabling, "");
			}
		}
		// stop arena
		arena.getGameHandler().stopArena();
		// stop countdown
		arena.getGameHandler().stopArenaCountdown();
		// stop antileave handler
		arena.getGameHandler().stopArenaAntiLeaveHandler();
		// regen gamelevels
		for (GameLevel gl : arena.getStructureManager().getGameLevels()) {
			gl.regen();
		}
		// modify signs
		arena.plugin.signEditor.modifySigns(arena.getArenaName());
	}

	public boolean isArenaStarting() {
		return starting;
	}

	public void setStarting(boolean starting) {
		this.starting = starting;
	}

	public boolean isArenaRunning() {
		return running;
	}

	public void setRunning(boolean running) {
		this.running = running;
	}

	public boolean isArenaRegenerating() {
		return regenerating;
	}

	public void setRegenerating(boolean regenerating) {
		this.regenerating = regenerating;
	}

}
