#include "player.h"
#include <sgg/graphics.h>
#include "bullet.h"
#include "gamestate.h"
#include <iostream>
#include <list>
#include <sgg/util.h>
#include <string>
#include "timer.h"
using namespace graphics;
using namespace std;

//Movement, explosion logic, shooting.
void Player::update(float dt) {
	if (m_is_exploding) {
		m_explosion_timer += dt / 1000.0f;
		//8 total exploding frames.
		m_explosion_frame = static_cast<int>((m_explosion_timer / 1.0f) * 8) + 1;

		if (m_explosion_frame > 8) {
			m_explosion_frame = 8;
		}
		//Explosions lasts for 1 second
		if (m_explosion_timer >= 1.0f) {
			m_active = false;
			m_is_exploding = false;
		}
	}
	else {
		//Avoid multiple collisions with the same object.
		if (m_recently_hit && !m_is_exploding) {
			m_timer.operator float();
			if (!m_timer) { 
				m_recently_hit = false;
			}
		}
		float delta_time = dt / 1000.0f;
		is_moving = false;

		if (getKeyState(SCANCODE_A)) {
			m_pos_x -= delta_time * m_velocity;
			is_moving = true;
		}
		if (getKeyState(SCANCODE_D)) {
			m_pos_x += delta_time * m_velocity;
			is_moving = true;
		}
		if (getKeyState(SCANCODE_W)) {
			m_pos_y -= delta_time * m_velocity;
			is_moving = true;
		}
		if (getKeyState(SCANCODE_S)) {
			m_pos_y += delta_time * m_velocity;
			is_moving = true;
		}
		if (getKeyState(SCANCODE_SPACE)) {
			float current_time = graphics::getGlobalTime() / 1000.0f;
			if (current_time - m_last_shot >= m_cooldown) {
				playSound(m_state->getFullAssetPath("shoot.mp3"), 0.3f, false);
				m_bullets.push_back(new Bullet(m_pos_x, m_pos_y - 0.3f));
				b_count++;
				m_last_shot = current_time;
			}
		}

		for (auto it = m_bullets.begin(); it != m_bullets.end(); ) {
			(*it)->update(dt);
			if ((*it)->outOfBounds(m_state->getCanvasWidth(), m_state->getCanvasHeight())) {
				delete* it;
				it = m_bullets.erase(it);
			}
			else {
				++it;
			}
		}
		if (m_pos_x > m_state->getCanvasWidth()) {
			m_pos_x = 0;
		}
		if (m_pos_x < 0) {
			m_pos_x = m_state->getCanvasWidth();
		}
		if (m_pos_y > m_state->getCanvasHeight()) {
			m_pos_y = 0;
		}
		if (m_pos_y < 0) {
			m_pos_y = m_state->getCanvasHeight();
		}
		m_state->m_global_offset_x = m_state->getCanvasWidth() / 2.0f - m_pos_x;
		m_state->m_global_offset_y = m_state->getCanvasHeight() / 2.0f - m_pos_y;
		GameObject::update(dt);
	}
}
void Player::init()
{

	m_pos_x = m_state->getCanvasWidth() / 2.0f;
	m_pos_y = m_state->getCanvasHeight() / 2.0f;

	m_brush_player.outline_opacity = 0.0f;
	m_brush_player.texture = m_state->getFullAssetPath("spaceship.png");
	m_brush_flame.outline_opacity = 0.0f;
	m_brush_flame.texture = m_state->getFullAssetPath("flame.png");
}

void Player::draw()
{
	if (m_is_exploding) {
		m_brush_player.texture = m_state->getFullAssetPath("exp" + std::to_string(m_explosion_frame) + ".png");
		m_brush_player.outline_opacity = 0.0f;

		drawRect(m_pos_x, m_pos_y, 1.0f, 1.0f, m_brush_player);
		playSound(m_state->getFullAssetPath("explosion.wav"), 0.3f, false);
	}
	else {
		//When player is hit paint the ship red.
		if (m_recently_hit && !m_is_exploding) {
			m_brush_player.fill_color[0] = 1.0f; 
			m_brush_player.fill_color[1] = 0.0f;
			m_brush_player.fill_color[2] = 0.0f;
			playSound(m_state->getFullAssetPath("hit.mp3"), 1.0f, false);
		}
		else {
			m_brush_player.fill_color[0] = 1.0f;
			m_brush_player.fill_color[1] = 1.0f;
			m_brush_player.fill_color[2] = 1.0f;
		}
		if (!is_moving) {
			drawRect(m_pos_x, m_pos_y, 0.6f, 0.5f, m_brush_player);
		}
		//Draw flashing flame when moving.
		else {
			drawRect(m_pos_x, m_pos_y, 0.6f, 0.5f, m_brush_player);
			drawRect(m_pos_x, m_pos_y + 0.48f, 0.6f, 0.5f, m_brush_flame);
			float p = 0.4f + fabs(cos(getGlobalTime() / 300));
			SETCOLOR(m_brush_flame.fill_color, p, p, p);
		}
		for (auto bullet : m_bullets) {
			bullet->draw();
		}
	}

}

list<Bullet*>& Player::getBullets() 
{
	return m_bullets;
}

Disk Player::getCollisionHull() const
{
	Disk disk;
	disk.cx = m_pos_x;
	disk.cy = m_pos_y;
	disk.radius = 0.3f;

	return disk;
}
void Player::markHit() {
	m_recently_hit = true;
	m_timer.start();
}

void Player::resetHit() {
	m_recently_hit = false;
}

bool Player::recentlyHit() const {
	return m_recently_hit;
}
