delete from users where user_id > 1000
DBCC CHECKIDENT ('[users]', RESEED, 1000)

delete from session where session_id > 1000
DBCC CHECKIDENT ('[session]', RESEED, 1000)

delete from message where 1 = 1
DBCC CHECKIDENT ('[message]', RESEED, 0)