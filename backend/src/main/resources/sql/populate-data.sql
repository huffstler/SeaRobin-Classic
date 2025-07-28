insert into searobin.anglers(username,created_on) values
('foo', now()),('bar', now()),('baz', now());

insert into searobin.organization(org_name, created_on) values
('Sea Robin Classic', now()),
('Trout Bout', now());

-- create new tournament in an existing organization
with src_org as (
	select * from searobin.organization where org_name = 'Sea Robin Classic'
)
insert into searobin.tournaments(organization_id, tournament_name, created_on) 
select src_org.id, src_org.org_name, now() from src_org;

-- create a new membership between an organization, angler, and tournament
with tourney as (
	select * from searobin.tournaments where tournament_name = 'The Sea Robin Classic'
),
angler as (
	select id from searobin.anglers where username = 'foo'
)
insert into searobin.membership(organization_id, tournament_id, angler_id)
select tourney.organization_id,tourney.id, angler.id from tourney, angler;
--select * from searobin.membership;

-- create a new catch for an angler in a tournament
with tourney as (
	select * from searobin.tournaments where tournament_name = 'The Sea Robin Classic'
),
angler as (
	select * from searobin.anglers where username = 'foo'
),
fish as (
	select * from searobin.fish where name = 'Black Drum'
)
insert into searobin.catches(tournament_id, angler_id, fish_id, fish_length, catch_time)
select tourney.id, angler.id, fish.id, 10, now() from tourney, angler, fish;
--select * from searobin.catches;

insert into searobin.tiers(name) values
('GAME'),
('SPORT'),
('TRASH'),
('SHINE'),
('SEA ROBIN');
select * from searobin.tiers;

insert into searobin.fish(name,tier_id) values
('Striped Bass',1),
('Flounder',	1),
('Red Drum',	1),
('Sheepshead',	2),
('Spot',		2),
('Blue Fish',	2),
('Croaker',		2),
('Silver Perch',2),
('Kingfish',	2),
('Eel',			2),
('Black Drum',	2),
('Sea Trout',	2),
('Spotted Hake',2),
('Cusk Eel',	2),
('Shark',		3),
('Skate',		3),
('Ray',			3),
('Toadfish',	3),
('Oyster Cracker',3),
('Stargazer',	3),
('Pufferfish',	3),
('Sea Robin',	5);

select * from searobin.fish;
