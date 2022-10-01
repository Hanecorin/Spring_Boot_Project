package probono.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import probono.model.ProbonoService;
import probono.model.dto.ActivistDTO;
import probono.model.dto.ProbonoProjectDTO;
import probono.model.dto.RecipientDTO;

@RestController
public class ProbonoRestController {

	@Autowired
	ProbonoService probonoService;
	//진행중인 모든 프로젝트 검색 로직
	//view - probonoProjectListAjax.jsp
	/* 비동기 요청 처리 
	 * - @ResponseBody 는 @RestController의 클래스에선 생략
	 * - 요청과 응답 구조  
	 * - 응답 구조는 직접 client의 요청 객체에 주고, js의 비동기 객체는 데이터 받아서 ui에 적합하게 data를 출력 
	 * - 검색된 데이터를 json 객체들의 배열 형식으로 응답
	 * - client js 비동기 함수에선 json 포멧으로 데이터 하나하나 뽑아서 table 구조를 형성
	 *  
	 * 개발 형식 확인 - 응답 데이터가 json 배열 형식?
	 * http://localhost:8080/step11_refactoring/probono/probonoProjectAll
	 */
	@GetMapping("probono/probonoProjectAll")
	public ArrayList<ProbonoProjectDTO> probonoProjectAll() throws Exception {
		System.out.println("---------ajax-----------");
		return probonoService.getAllProbonoProjects();
	}
	
	@GetMapping("probono/activistAll")
	public ArrayList<ActivistDTO> activistAll() throws Exception {
		return probonoService.getAllActivists();
	}
	
	@GetMapping("probono/activistInsert")
	public boolean activistInsert(ActivistDTO activist) throws Exception {
		return probonoService.addActivist(activist);
	}
	@GetMapping("probono/recipientAll")
	public ArrayList<RecipientDTO> recipientAll() throws Exception {
		return probonoService.getAllRecipients();
	}
	@GetMapping("probono/recipientInsert")
	public boolean recipientInsert(RecipientDTO recipient) throws Exception {
		return probonoService.addRecipient(recipient);
	}
	@GetMapping("probono/activist")
	public ActivistDTO activist(@RequestParam String activistId) throws Exception {
		
		return probonoService.getActivist(activistId);
	}
	
}