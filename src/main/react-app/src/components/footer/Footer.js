import moment from 'moment'

const Footer = () => {
  return (
    <footer className='bg-dark text-white-50 text-center p-3'>
      <p>Copyright &copy; Sell My Picture {moment(new Date()).format('YYYY')}</p>
    </footer>
  )
}

export default Footer