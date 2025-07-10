#pragma once
#include "gameobject.h"
#include "sgg/graphics.h"
#include "asteroid.h"
#include <list>
#include "powerup.h"
using namespace graphics;

class Level : public GameObject {
	Brush m_brush_background;
	float m_center_x = 5.0f;
	float m_center_y = 5.0f;
	std::list<Asteroid*> m_asteroids;
	std::list<PowerUp*> m_powerups;
	float m_asteroid_timer = 0.0f;
	float m_spawn_interval = 1.0f;
	float m_powerup_spawn = 0.0f;
	float m_powerup_timer = 0.0f;
	bool checkCollision();
	bool checkCollisionBullet();
	bool m_collision = false; 
	float m_collision_timer = 0.0f;    
	const float delay = 3.0f;
	int score;
public:
	void update(float dt) override;
	void init() override;
	void draw() override;
	void setScore(int score) { score = score; };
	int getScore() { return score; }
	Level(const std::string& name = "Level0");
	~Level();
};