/**
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package edu.dfci.cccb.gameon.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.NativeWebRequest;

import edu.dfci.cccb.gameon.domain.termsofuse.TermsOfUseChecker;
 
/**
 * @author levk
 * 
 */
@Controller
@RequestMapping ("/")
public class HomeController {

  @Autowired private TermsOfUseChecker termsOfUseCheck; 
	
  @RequestMapping (method = RequestMethod.GET)
  public String home (Model uiModel) {
    return "redirect:/snps?find=ajax&amp;build=&amp;strand=&amp;NStudy=&amp;effectAllele=&amp;refAllele=";
  }
  
  @RequestMapping (value="termsofuse", method = RequestMethod.GET)
  public String termsOfUse(Model uiModel){	  
	  return "termsofuse";
  }
  
  @RequestMapping (value="termsofuse", method = RequestMethod.POST, params={"btnAccept"})
  public String acceptTermsOfUse(NativeWebRequest request,  Model uiModel,
		  @RequestParam(value="accepted", required=false) boolean isAccepted){
	  if(!isAccepted){
		  uiModel.addAttribute("error", "You must accept the Terms Of Use to proceed");
		  return termsOfUse(uiModel);
	  }
	  
	  termsOfUseCheck.setAccepted(isAccepted);
	  return home(uiModel);
  }
  
  @RequestMapping (value="termsofuse", method = RequestMethod.POST, params={"btnDecline"})
  public String declinieTermsOfUse(){
	  return "redirect:/resources/j_spring_security_logout";
  }
}
