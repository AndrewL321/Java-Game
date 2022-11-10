package Menu;

import java.nio.file.Path;
import java.nio.file.Paths;

import Main.Planets;
/**
 * Class used to load backgrounds for menus based of where player currently is
 */
public class BackgroundPlanetBuilder {
    public BackgroundPlanetBuilder(){
    }

    /**
     * Get the path to start level image for desired planet
     * @param planet int for index of planet
     * @return path to that planets image
     */

    public Path getPath(int planet){
        if(planet == 0 ){
            return Paths.get("Images/mercuryStartBackground.png");
        }
        else if(planet == 1 ){
            return Paths.get("Images/venusStartBackground.png");
        }
        else if(planet == 2 ){
            return Paths.get("Images/earthStartBackground.png");
        }
        else if(planet == 3 ){
            return Paths.get("Images/marsStartBackground.png");
        }
        else if(planet == 4 ){
            return Paths.get("Images/jupiterStartBackground.png");
        }
        else if(planet == 5 ){
            return Paths.get("Images/saturnStartBackground.png");
        }
        else if(planet == 6 ){
            return Paths.get("Images/uranusStartBackground.png");
        }
        else if(planet == 7 ){
            return Paths.get("Images/neptuneStartBackground.png");
        }
        return Paths.get("Images/earthStartBackground.png");
        

    }

    /**
     * Get path to end level image for desired planet
     * @param planet planet index to get for
     * @return Path to image
     */
    public Path getPathBack(Planets planet){
        if(planet.getName().equals("Mercury")){
            return Paths.get("Images/mercuryEndBackground.png");
        }
        else if(planet.getName().equals("Venus")){
            return Paths.get("Images/venusEndBackground.png");
        }
        else if(planet.getName().equals("Earth")){
            return Paths.get("Images/earthEndBackground.png");
        }
        else if(planet.getName().equals("Mars")){
            return Paths.get("Images/marsEndBackground.png");
        }
        else if(planet.getName().equals("Jupiter")){
            return Paths.get("Images/jupiterEndBackground.png");
        }
        else if(planet.getName().equals("Saturn") ){
            return Paths.get("Images/saturnEndBackground.png");
        }
        else if(planet.getName().equals("Uranus")){
            return Paths.get("Images/uranusEndBackground.png");
        }
        else if(planet.getName().equals("Neptune") ){
            return Paths.get("Images/neptuneEndBackground.png");
        }
        System.out.println("Failed to find background image " + planet.getName());
        return Paths.get("Images/earthEndBackground.png");
        

    }
}
