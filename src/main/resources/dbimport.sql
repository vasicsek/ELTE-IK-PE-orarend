
CALL SYSCS_UTIL.SYSCS_IMPORT_TABLE(
    null,'TEACHER','/tmp/teacher.tbl',null,null,null,0);

CALL SYSCS_UTIL.SYSCS_IMPORT_TABLE(
    null,'ROOM','/tmp/room.tbl',null,null,null,0);

CALL SYSCS_UTIL.SYSCS_IMPORT_TABLE(
    null,'SUBJECT','/tmp/subject.tbl',null,null,null,0);

CALL SYSCS_UTIL.SYSCS_IMPORT_TABLE(
    null,'SEMESTER','/tmp/semester.tbl',null,null,null,0);

CALL SYSCS_UTIL.SYSCS_IMPORT_TABLE(
    null,'SEMESTERITEM','/tmp/semesteritem.tbl',null,null,null,0);

CALL SYSCS_UTIL.SYSCS_IMPORT_TABLE(
    null,'SEMESTER_SEMESTERITEM','/tmp/semester_semesteritem.tbl',null,null,null,0);
