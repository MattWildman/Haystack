CREATE TABLE IF NOT EXISTS users (
  id INT NOT NULL AUTO_INCREMENT,
  firstName VARCHAR(50) NOT NULL,
  lastName VARCHAR(50) NOT NULL,
  userName VARCHAR(50) NOT NULL UNIQUE,
  password VARCHAR(50) NOT NULL,
  email VARCHAR (100) NOT NULL UNIQUE,
  picture LONGBLOB,
  pictureType VARCHAR(10),
  receiveEmails BIT(1),
  
  PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS connections (
  id INT NOT NULL AUTO_INCREMENT,
  userId INT NOT NULL,
  title VARCHAR(100) NOT NULL,
  summary TEXT,
  conType VARCHAR(10) NOT NULL,
  
  PRIMARY KEY (id),
  
  FOREIGN KEY (userId)
    REFERENCES users(id)
    ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS contexts (
	id INT NOT NULL AUTO_INCREMENT,
	conId INT NOT NULL,
	title VARCHAR(100) NOT NULL,
	summary TEXT,
	earliest DATETIME NOT NULL,
	latest DATETIME NOT NULL,
	locationType VARCHAR(10) NOT NULL,
	
	PRIMARY KEY (id),
  
    FOREIGN KEY (conId)
      REFERENCES connections(id)
      ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS locations (
  id INT NOT NULL AUTO_INCREMENT,
  ctxId INT NOT NULL,
  journeyId INT,
  name VARCHAR(100) NOT NULL,
  area VARCHAR(100) NOT NULL,
  postcode VARCHAR(20),
  country VARCHAR(50),
  description TEXT,
  lat FLOAT(8,6) NOT NULL,
  longd FLOAT(9,6) NOT NULL,
  rad INT NOT NULL,
  
  PRIMARY KEY (id),
  
  FOREIGN KEY (ctxId)
    REFERENCES contexts(id)
    ON DELETE CASCADE,
    
  FOREIGN KEY (journeyId)
    REFERENCES journeys(id)
    ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS journeys (
  id INT NOT NULL AUTO_INCREMENT,
  ctxId INT NOT NULL,
  startId INT NOT NULL,
  endId INT NOT NULL,
  type VARCHAR(20) NOT NULL,
  company VARCHAR(50),
  service VARCHAR(10),
  description TEXT,
  
  PRIMARY KEY (id),
  
  FOREIGN KEY (ctxId)
    REFERENCES contexts(id)
    ON DELETE CASCADE,
    
  FOREIGN KEY (startId)
    REFERENCES locations(id),
    
  FOREIGN KEY (endId)
    REFERENCES locations(id)
);

CREATE TABLE IF NOT EXISTS meetings (
  id INT NOT NULL AUTO_INCREMENT,
  conId INT NOT NULL,
  description TEXT,
  participantCount SMALLINT,
  userDesc INT NOT NULL,
  
  PRIMARY KEY (id),
  
  FOREIGN KEY (conId)
    REFERENCES connections(id)
    ON DELETE CASCADE,
    
  FOREIGN KEY (userDesc)
    REFERENCES participants(id)
);

CREATE TABLE IF NOT EXISTS participants (
  id INT NOT NULL AUTO_INCREMENT,
  meetingId INT NOT NULL,
  gender VARCHAR(20),
  minAge TINYINT,
  maxAge TINYINT,
  height TINYINT,
  description TEXT,
  
  PRIMARY KEY (id),
  
  FOREIGN KEY (meetingId)
    REFERENCES meetings(id)
	ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS lostItems (
  id INT NOT NULL AUTO_INCREMENT,
  conId INT NOT NULL,
  name VARCHAR(100),
  description TEXT NOT NULL,
  picture LONGBLOB,
  pictureType VARCHAR(10),
  
  PRIMARY KEY (id),
  
  FOREIGN KEY (conId)
    REFERENCES connections(id)
    ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS foundItems (
  id INT NOT NULL AUTO_INCREMENT,
  conId INT NOT NULL,
  name VARCHAR(100),
  description TEXT NOT NULL,
  picture LONGBLOB,
  pictureType VARCHAR(10),
  
  PRIMARY KEY (id),
  
  FOREIGN KEY (conId)
    REFERENCES connections(id)
    ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS messages (
  id INT NOT NULL AUTO_INCREMENT,
  toUser INT NOT NULL,
  fromUser INT,
  msgType VARCHAR(20),
  isRead BIT(1),
  subject VARCHAR(150),
  content MEDIUMTEXT,

  PRIMARY KEY (id),
  
  FOREIGN KEY (toUser)
    REFERENCES users(id)
    ON DELETE CASCADE, 

  FOREIGN KEY (fromUser)
    REFERENCES users(id)
    ON DELETE SET NULL      
  
);

CREATE TABLE IF NOT EXISTS candidates (
  id INT NOT NULL AUTO_INCREMENT,
  userId INT NOT NULL,
  userConId INT NOT NULL,
  candConId INT NOT NULL,
  
  PRIMARY KEY (id),
  
  UNIQUE (userId, userConId, candConId),
  
  FOREIGN KEY (userId)
    REFERENCES users(id)
    ON DELETE CASCADE,
	
  FOREIGN KEY (userConId)
    REFERENCES connections(id)
    ON DELETE CASCADE,

  FOREIGN KEY (candConId)
    REFERENCES connections(id)
    ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS sharedConnections (
  id INT NOT NULL AUTO_INCREMENT,
  conId1 INT NOT NULL,
  conId2 INT NOT NULL,
  
  PRIMARY KEY (id),
  
  UNIQUE (conId1, conId2),
	
  FOREIGN KEY (conId1)
    REFERENCES connections(id),

  FOREIGN KEY (conId2)
    REFERENCES connections(id)
);

CREATE TABLE IF NOT EXISTS blockings (
  userId INT NOT NULL,
  blockedId INT NOT NULL,
  
  PRIMARY KEY (userId, blockedId),
  
  FOREIGN KEY (userId)
    REFERENCES users(id)
    ON DELETE CASCADE, 

  FOREIGN KEY (blockedId)
    REFERENCES users(id)
    ON DELETE CASCADE,    
);
