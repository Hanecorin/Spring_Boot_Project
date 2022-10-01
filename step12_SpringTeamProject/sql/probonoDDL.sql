--show databases;

USE playdata;

-- 재능 기부자
DROP TABLE IF EXISTS activist;

-- 재능 수혜자
DROP TABLE IF EXISTS recipient;
 
-- 재능기부 정보
DROP TABLE IF EXISTS probono;

-- 재능기부 프로젝트
DROP TABLE IF EXISTS probono_project;

CREATE TABLE activist (
       activist_id          	VARCHAR(20)  PRIMARY KEY,
       name               	VARCHAR(20) NOT NULL,
       password         	VARCHAR(20) NOT NULL,
       major                	VARCHAR(50) NOT NULL
);

CREATE TABLE recipient (
       recipient_id        		VARCHAR(20) PRIMARY KEY,
       name                		VARCHAR(20) NOT NULL,
       password          		VARCHAR(20) NOT NULL,
       receiveHopeContent   VARCHAR(50) NOT NULL
);


CREATE TABLE probono (
       probono_id          	VARCHAR(50) PRIMARY KEY,
       probono_name      VARCHAR(50) NOT NULL,
       probono_purpose  	VARCHAR(200) NOT NULL
);

CREATE TABLE probono_project (
	   probono_project_id     		INT AUTO_INCREMENT PRIMARY KEY,
	   probono_project_name 		VARCHAR(50) NOT NULL,
       probono_id           			VARCHAR(50) NOT NULL,       
       activist_id          				VARCHAR(20) NOT NULL,
       receive_id           				VARCHAR(20) NOT NULL, 
       project_content      			VARCHAR(100) NOT NULL
);

ALTER TABLE probono_project AUTO_INCREMENT = 10000;
ALTER TABLE probono_project  ADD FOREIGN KEY (receive_id) REFERENCES recipient  (recipient_id);
ALTER TABLE probono_project ADD FOREIGN KEY (activist_id)  REFERENCES activist  (activist_id);
ALTER TABLE probono_project ADD FOREIGN KEY (probono_id) REFERENCES probono  (probono_id);
