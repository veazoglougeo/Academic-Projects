#include "level.h"
#include "gamestate.h"
#include "player.h"
#include "asteroid.h"
#include "sgg/util.h"
#include <iostream>
#include <chrono>
#include <thread>

/*Check if player collides with asteroid/*/
bool Level::checkCollision() {
	if (!m_state->getPlayer()->isActive()) {
		return false;
	}

	Disk d1 = m_state->getPlayer()->getCollisionHull();
	for (auto asteroid : m_asteroids) {
		Disk d2 = asteroid->getCollisionHull();
		float dx = d1.cx - d2.cx;
		float dy = d1.cy - d2.cy;

		if (sqrt(dx * dx + dy * dy) < d1.radius + d2.radius) {
			/*Decrease health points by 1*/
			if (!(m_state->getPlayer()->recentlyHit())) {
				m_state->getPlayer()->decreaseHp();
				/*Decrease speed by 0.5 if speed is greater than the minimum 1.5*/
				if (m_state->getPlayer()->getVelocity() > 1.5f) {
					m_state->getPlayer()->decreaseVelocity();
				}
				m_state->getPlayer()->markHit();

				if (m_state->getPlayer()->getHp() <= 0) {
					m_state->getPlayer()->setExploding(true);
					m_collision = true;
					m_collision_timer = 0.0f;
				}
			}
			return true;
		}
	}

	m_state->getPlayer()->resetHit();
	return false;
}
/*Check if bullet collides with asteroid.*/
bool Level::checkCollisionBullet() {
	if (!m_state->getPlayer()->isActive()) {
		return false;
	}

	auto& bullets = m_state->getPlayer()->getBullets();

	for (auto bullet_it = bullets.begin(); bullet_it != bullets.end();) {
		Bullet* bullet = *bullet_it;
		if (!bullet) {
			bullet_it = bullets.erase(bullet_it);
			continue;
		}

		Disk bulletHull = bullet->getCollisionHull();
		bool bulletHit = false;

		for (auto asteroid_it = m_asteroids.begin(); asteroid_it != m_asteroids.end();) {
			Asteroid* asteroid = *asteroid_it;
			if (!asteroid) {
				asteroid_it = m_asteroids.erase(asteroid_it);
				continue;
			}

			Disk asteroidHull = asteroid->getCollisionHull();

			float dx = bulletHull.cx - asteroidHull.cx;
			float dy = bulletHull.cy - asteroidHull.cy;

			if (sqrt(dx * dx + dy * dy) < bulletHull.radius + asteroidHull.radius) {
				asteroid->hit();
				delete bullet;
				bullet_it = bullets.erase(bullet_it);
				bulletHit = true;

				if (asteroid->isDestroyed()) {
					score += asteroid->getPoints();
					setScore(score);
					playSound(m_state->getFullAssetPath("asteroid.mp3"), 1.0f, false);
					delete asteroid;
					asteroid_it = m_asteroids.erase(asteroid_it);
				}
				else {
					++asteroid_it; 
				}
				break;
			}
			else {
				++asteroid_it;
			}
		}

		if (!bulletHit) {
			++bullet_it;
		}
	}

	return true;
}
void Level::update(float dt) {
	m_asteroid_timer += dt / 1500.0f;
	/*Make the game harder depending on score.*/
	if (score > 50) {
		m_asteroid_timer += dt / 1300.0f;
	}else if (score > 70) {
		m_asteroid_timer += dt / 1200.0f;
	}
	else if (score > 100) {
		m_asteroid_timer += dt / 1000.0f;
	}
	else if (score > 140) {
		m_asteroid_timer += dt / 800.0f;
	}
	else if (score > 200) {
		m_asteroid_timer += dt / 650.0f;
	}
	else if (score > 250) {
		m_asteroid_timer += dt / 400.0f;
	}
	else if (score > 350) {
		m_asteroid_timer += dt / 350.0f;
	}

	if (m_asteroid_timer >= m_spawn_interval) {
		//Min x axis = 0.0f, max = canvas width.
		float random_x = static_cast<float>(rand()) / RAND_MAX * m_state->getCanvasWidth();
		//Min size = 0.3f, max = 1.0f.
		float random_size = 0.3f + static_cast<float>(rand()) / RAND_MAX * 0.7f;
		//Min velocity = 1.5f, max = 3.0f.
		float random_velocity = 1.5f + static_cast<float>(rand()) / RAND_MAX * 1.5f;
		m_asteroids.push_back(new Asteroid(random_x, random_size, random_velocity));
		//Reset asteroid spawn timer.
		m_asteroid_timer = 0.0f;
		//Spawn randomly asteroids and avoid double spawning.
		if (m_powerup_spawn >= 1000.0f && rand() % 15 == 0) {
			//Max player health points is 3.
			bool canSpawnHealth = m_state->getPlayer()->getHp() < 3;
			//Max player velocity is 4.5f.
			bool canSpawnSpeed = m_state->getPlayer()->getVelocity() < 4.5f;
			if (!canSpawnHealth && !canSpawnSpeed) {
				return;
			}
			PowerUp::Type type = (rand() % 2 == 0) ? PowerUp::health : PowerUp::speed;

			if (type == PowerUp::health && !canSpawnHealth) {
				type = PowerUp::speed;
			}
			else if (type == PowerUp::speed && !canSpawnSpeed) {
				type = PowerUp::health;
			}
			m_powerups.push_back(new PowerUp(random_x, 0.3f, type));
			//Reset powerup spawn time.
			m_powerup_spawn = 0.0f;
		}

	}
	m_powerup_spawn += dt;

	for (auto it = m_powerups.begin(); it != m_powerups.end();) {
		(*it)->update(dt);
		if ((*it)->getCollisionHull().radius + m_state->getPlayer()->getCollisionHull().radius >
			sqrt(pow((*it)->getX() - m_state->getPlayer()->getPosX(), 2) +
				pow((*it)->gety() - m_state->getPlayer()->getPosY(), 2))) {

			playSound(m_state->getFullAssetPath("power-up.mp3"), 1.0f, false);
			if ((*it)->getType() == PowerUp::health) {
				m_state->getPlayer()->increaseHp();
			}
			else if ((*it)->getType() == PowerUp::speed) {
				m_state->getPlayer()->increaseVelocity();

			}

			delete* it;
			it = m_powerups.erase(it);
		}
		else if ((*it)->outOfBounds(m_state->getCanvasHeight())) {
			delete* it;
			it = m_powerups.erase(it);
		}
		else {
			++it;
		}
	}

	for (auto it = m_asteroids.begin(); it != m_asteroids.end();) {
		(*it)->update(dt);
		if ((*it)->outOfBounds(m_state->getCanvasHeight())) {
			delete* it;
			it = m_asteroids.erase(it);
		}
		else {
			++it;
		}
	}


	if (m_collision) {
		if (m_state->getPlayer()->isExploding()) {
			m_state->getPlayer()->update(dt); 
		}
		else {
			m_state->getInstance()->setState("game_over");
		}
	}
	else {
		if (m_state->getPlayer()->isActive()) {
			m_state->getPlayer()->update(dt);
		}
	}

	checkCollision();
	checkCollisionBullet();
	GameObject::update(dt);
}
void Level::init()
{
}

void Level::draw()
{
	float w = m_state->getCanvasWidth();
	float h = m_state->getCanvasHeight();
	drawRect(w / 2.0f, h / 2.0f, w, h, m_brush_background);
	float p = 0.4f + fabs(cos(getGlobalTime() / 3000));
	SETCOLOR(m_brush_background.fill_color, p, p, p);

	if (m_state->getPlayer()->isActive()) {
		m_state->getPlayer()->draw();
	}
	for (auto asteroid : m_asteroids) {
		asteroid->draw();
	}

	resetPose();
	for (auto powerup : m_powerups) {
		powerup->draw();
	}
	Brush br;
	string disp_score = to_string(score);
	string hp = to_string(m_state->getPlayer()->getHp());
	drawText(w / 2.15, h / 6, 0.3, disp_score, br);
	drawText(w / 1.15, h / 1.04, 0.3, hp, br);
	br.texture = m_state->getFullAssetPath("hp.png");
	br.outline_opacity = 0.0f;
	drawRect(w / 1.05, h / 1.05, 0.3, 0.3, br);
}

Level::Level(const std::string& name)
	:GameObject(name)
{
	m_brush_background.outline_opacity = 0.0f;
	m_brush_background.texture = m_state->getFullAssetPath("background.png");
}

Level::~Level()
{
}
