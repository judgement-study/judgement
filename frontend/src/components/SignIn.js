import React, { useState } from 'react';
import axios from 'axios';
import './Header.css';

function SignIn() {
  const [formData, setFormData] = useState({
    loginId: '',
    password: ''
  });

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData({
      ...formData,
      [name]: value,
    });
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    axios.post('http://localhost:5000/signIn', formData)
      .then((response) => {
        console.log(response.data);
        // 로그인 성공 시 필요한 처리
      })
      .catch((error) => {
        console.error('There was an error!', error);
      });
  };

  return (
    <div className="form-container">
      <h2>로그인</h2>
      <form onSubmit={handleSubmit}>
        <input type="text" name="loginId" placeholder="아이디" onChange={handleChange} required />
        <input type="password" name="password" placeholder="비밀번호" onChange={handleChange} required />
        <button type="submit">로그인</button>
      </form>
    </div>
  );
}

export default SignIn;
